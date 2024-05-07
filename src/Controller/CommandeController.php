<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Entity\User;
use App\Form\CommandeType;
use App\Repository\CommandeRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\ItemRepository;
use App\Repository\UserRepository;

#[Route('/commande')]
class CommandeController extends AbstractController
{
    #[Route('/', name: 'app_commande_index', methods: ['GET'])]
    public function index(CommandeRepository $commandeRepository): Response
    {
        return $this->render('commande/index.html.twig', [
            'commandes' => $commandeRepository->findAll(),
        ]);
    }

    #[Route('/new/{id}', name: 'app_commande_new', methods: ['GET', 'POST'])]
    public function new(int $id, Request $request, EntityManagerInterface $entityManager, ItemRepository $itemRepository, UserRepository $userRepository): Response
    {
        $commande = new Commande();
        $item = $itemRepository->find($id);

        $user = $this->getUser();

        if (!$item || !$user) {
            throw $this->createNotFoundException('The item or user does not exist');
        }

        $commande->setItem($item);
        $commande->setUser($user);
        $commande->setDateC(new \DateTime());
        $commande->setTotal($item->getPrix());

        $entityManager->persist($commande);
        $entityManager->flush();

        return $this->redirectToRoute('app_item_indexUser', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/{id}', name: 'app_commande_show', methods: ['GET'])]
    public function show(int $id, CommandeRepository $commandeRepository): Response
    {
        $user = $this->getDoctrine()->getRepository(User::class)->find($id);

        if (!$user) {
            throw $this->createNotFoundException('User not found');
        }

        $commandes = $commandeRepository->findBy(['user' => $user]);

        return $this->render('commande/show.html.twig', [
            'commandes' => $commandes,
        ]);
    }

    #[Route('/{id}/admin', name: 'app_commande_showAdmin', methods: ['GET'])]
    public function showAdmin(int $id, CommandeRepository $commandeRepository): Response
    {
        $user = $this->getDoctrine()->getRepository(User::class)->find($id);

        if (!$user) {
            throw $this->createNotFoundException('User not found');
        }

        $commandes = $commandeRepository->findBy(['user' => $user]);

        return $this->render('commande/showAdmin.html.twig', [
            'commandes' => $commandes,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_commande_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Commande $commande, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_commande_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commande/edit.html.twig', [
            'commande' => $commande,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_commande_delete', methods: ['POST'])]
    public function delete(Request $request, $id, CommandeRepository $commandeRepository): Response
    {
        $commande = $commandeRepository->find($id);

        if (!$commande) {
            throw $this->createNotFoundException('No commande found for id ' . $id);
        }
        if ($this->isCsrfTokenValid('delete' . $commande->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($commande);
            $entityManager->flush();
        }

        $userId = $this->getUser()->getId();

        return $this->redirectToRoute('app_item_index');
    }

    #[Route('/{id}/delete', name: 'app_commande_delete_group', methods: ['POST'])]
    public function deleteGroup(Request $request, $id): Response
    {
        $user = $this->getDoctrine()->getRepository(User::class)->find($id);

        if (!$user) {
            throw $this->createNotFoundException('No user found for id ' . $id);
        }

        if ($this->isCsrfTokenValid('delete' . $user->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $commandes = $this->getDoctrine()->getRepository(Commande::class)->findBy(['user' => $user]);

            foreach ($commandes as $commande) {
                $entityManager->remove($commande);
            }

            $entityManager->flush();
        }

        return $this->redirectToRoute('app_item_index');
    }
}
