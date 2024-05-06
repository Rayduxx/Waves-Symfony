<?php

namespace App\Controller;

use App\Entity\Item;
use App\Form\ItemType;
use App\Repository\ItemRepository;
use App\Repository\CommandeRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use SpotifyWebAPI\SpotifyWebAPI;
use SpotifyWebAPI\Session;




#[Route('/item')]
class ItemController extends AbstractController
{
    #[Route('/', name: 'app_item_index', methods: ['GET', 'POST'])]
    public function index(Request $request, ItemRepository $itemRepository, CommandeRepository $commandeRepository, EntityManagerInterface $entityManager): Response
    {
        $item = new Item();
        $form = $this->createForm(ItemType::class, $item);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($item);
            $entityManager->flush();
        }

        $items = $itemRepository->findAll();
        $commandes = $commandeRepository->findAll();

        $groupedCommandes = [];

        foreach ($commandes as $commande) {
            $userId = $commande->getUser()->getId();

            if (!isset($groupedCommandes[$userId])) {
                $groupedCommandes[$userId] = [
                    'user' => $commande->getUser(),
                    'items' => [],
                    'total' => 0,
                    'dateC' => $commande->getDateC(),
                ];
            }

            $groupedCommandes[$userId]['items'][] = $commande->getItem();
            $groupedCommandes[$userId]['total'] += $commande->getTotal();

            if ($commande->getDateC() > $groupedCommandes[$userId]['dateC']) {
                $groupedCommandes[$userId]['dateC'] = $commande->getDateC();
            }
        }

        $sort = $request->query->get('sort');

        if ($sort === 'titre') {
            $items = $itemRepository->findBy([], ['titre' => 'ASC']);
        } elseif ($sort === 'auteur') {
            $items = $itemRepository->findBy([], ['auteur' => 'ASC']);
        } elseif ($sort === 'prix') {
            $items = $itemRepository->findBy([], ['prix' => 'ASC']);
        }

        return $this->render('item/index.html.twig', [
            'items' => $items,
            'form' => $form->createView(),
            'groupedCommandes' => $groupedCommandes,
        ]);
    }

    #[Route('/marketplace', name: 'app_item_indexUser', methods: ['GET', 'POST'])]
    public function indexUser(Request $request, ItemRepository $itemRepository, EntityManagerInterface $entityManager): Response
    {
        $item = new Item();
        $form = $this->createForm(ItemType::class, $item);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($item);
            $entityManager->flush();
        }

        $items = $itemRepository->findAll();

        $session = new Session(
            '6b7edfd326df43deb87ff4da4cde34a4',
            'da79d69b18d04a51b75b6835cab3461d'
        );
        $session->requestCredentialsToken();
        $accessToken = $session->getAccessToken();

        $api = new SpotifyWebAPI();
        $api->setAccessToken($accessToken);

        $previews = [];
        foreach ($items as $item) {
            $results = $api->search($item->getTitre() . ' ' . $item->getAuteur(), 'track');

            if (!empty($results->tracks->items)) {
                $track = $results->tracks->items[0];
                $previews[$item->getId()] = $track->preview_url;
            } else {
                $previews[$item->getId()] = null;
            }
        }

        $sort = $request->query->get('sort');

        if ($sort === 'titre') {
            $items = $itemRepository->findBy([], ['titre' => 'ASC']);
        } elseif ($sort === 'auteur') {
            $items = $itemRepository->findBy([], ['auteur' => 'ASC']);
        } elseif ($sort === 'prix') {
            $items = $itemRepository->findBy([], ['prix' => 'ASC']);
        } else {
            $items = $itemRepository->findAll();
        }

        return $this->render('item/indexUser.html.twig', [
            'items' => $items,
            'previews' => $previews,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/new', name: 'app_item_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $item = new Item();
        $form = $this->createForm(ItemType::class, $item);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($item);
            $entityManager->flush();

            return $this->redirectToRoute('app_item_indexUser', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('item/new.html.twig', [
            'item' => $item,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_item_show', methods: ['GET'])]
    public function show(int $id, ItemRepository $itemRepository): Response
    {
        $item = $itemRepository->find($id);

        if (!$item) {
            throw $this->createNotFoundException('The item does not exist');
        }

        return $this->render('item/show.html.twig', [
            'item' => $item,
        ]);
    }

    #[Route('/marketplace/{id}', name: 'app_item_showUser', methods: ['GET'])]
    public function showUser(int $id, ItemRepository $itemRepository): Response
    {
        $item = $itemRepository->find($id);

        if (!$item) {
            throw $this->createNotFoundException('The item does not exist');
        }

        return $this->render('item/showUser.html.twig', [
            'item' => $item,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_item_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, EntityManagerInterface $entityManager, ItemRepository $itemRepository, int $id): Response
    {
        $item = $itemRepository->find($id);

        if (!$item) {
            throw $this->createNotFoundException('The item does not exist');
        }

        $form = $this->createForm(ItemType::class, $item);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_item_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('item/edit.html.twig', [
            'item' => $item,
            'form' => $form,
        ]);
    }
    #[Route('/{id}/editUser', name: 'app_item_editUser', methods: ['GET', 'POST'])]
    public function editUser(Request $request, EntityManagerInterface $entityManager, ItemRepository $itemRepository, int $id): Response
    {
        $item = $itemRepository->find($id);

        if (!$item) {
            throw $this->createNotFoundException('The item does not exist');
        }

        $form = $this->createForm(ItemType::class, $item);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_item_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('item/edit.html.twig', [
            'item' => $item,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_item_delete', methods: ['POST'])]
    public function delete(Request $request, EntityManagerInterface $entityManager, ItemRepository $itemRepository, int $id): Response
    {
        $item = $itemRepository->find($id);

        if (!$item) {
            throw $this->createNotFoundException('The item does not exist');
        }

        if ($this->isCsrfTokenValid('delete' . $item->getId(), $request->request->get('_token'))) {
            $entityManager->remove($item);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_item_index', [], Response::HTTP_SEE_OTHER);
    }
}
