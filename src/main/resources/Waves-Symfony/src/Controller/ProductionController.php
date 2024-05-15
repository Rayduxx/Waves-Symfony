<?php

namespace App\Controller;

use App\Entity\Production;
use App\Entity\User;
use App\Form\NewProdType;
use App\Repository\ProductionRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\ORM\EntityManagerInterface;

class ProductionController extends AbstractController
{
    #[Route('/editprod/{id}', name: 'app_production_edit')]
    public function EditProd($id,EntityManagerInterface $entityManager,ProductionRepository $productionRepository, Request $request): Response
    {
        $prodProd = $productionRepository->find($id);
        $form = $this->createForm(NewProdType::class, $prodProd);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $Coverfile = $form['cover']->getData();
            if ($Coverfile) {
                $filename = md5(uniqid()) . '.' . $Coverfile->guessExtension();
                $Coverfile->move($this->getParameter('images_directory'), $filename);
                $prodProd->setCover($filename);
            }
            $entityManager->flush();
            return $this->redirectToRoute('app_production_list');
        }
        return $this->render('production/modifierProd.html.twig', [
            'prodProd' => $prodProd,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/Studio', name: 'app_studio')]
    public function Studio(): Response
    {
        return $this->render('production/index.html.twig', [
            'controller_name' => 'ProductionController',
        ]);
    }
    #[Route('/new-prod', name: 'app_production_new')]
    public function AddProd(Request $request, EntityManagerInterface $entityManager): Response
    {
        $prod = new Production();
        $form = $this->createForm(NewProdType::class, $prod);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $Coverfile = $form['cover']->getData();
            if ($Coverfile) {
                $filename = md5(uniqid()) . '.' . $Coverfile->guessExtension();
                $Coverfile->move($this->getParameter('images_directory'), $filename);
                $prod->setCover($filename);
            }
            $user = $this->getUser();
            $prod->setUser($user);
            $user->setProjets($user->getProjets() + 1);
            $entityManager->persist($prod);
            $entityManager->persist($user);
            $entityManager->flush();
            return $this->redirect('https://beets.studio/');
        }
        return $this->render('production/newprod.html.twig', ['form' => $form->createView(),]);
    }
    #[Route('/prod-list', name: 'app_production_list')]
    public function ProdList(ProductionRepository $productionRepository): Response
    {
        return $this->render(
            'production/prodlist.html.twig',
            [
                'prods' => $productionRepository->findAll()
            ]
        );
    }
}
