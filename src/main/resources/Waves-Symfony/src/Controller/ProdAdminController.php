<?php

namespace App\Controller;

use App\Entity\Production;
use App\Form\NewProdType;
use App\Repository\ProductionRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;

class ProdAdminController extends AbstractController
{
    #[Route('/proddashboard', name: 'app_prod_dashboard')]
    public function index(ProductionRepository $productionRepository): Response
    {
        return $this->render('dashboard/proddashboard.html.twig', [
            'prod' => $productionRepository->findAll(),
        ]);
    }
    #[Route('/ProdAdminSupp/{id}', name: 'app_prodSupp_dashboard')]
    public function ProdSuppAdmin($id): Response
    {
        $prod=$this->getDoctrine()->getRepository(Production::class)->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($prod);
        $em->flush();
        return $this->redirectToRoute('app_prod_dashboard');
    }
    #[Route('/ProdAdminModifier/{id}', name: 'app_prodEdit_dashboard')]
    public function EditProdAdmin($id,ProductionRepository $productionRepository, Production $production, Request $request): Response
    {
        $prodinfo=$productionRepository->find($id);
        $form = $this->createForm(NewProdType::class, $prodinfo);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($prodinfo);
            $entityManager->flush();
            return $this->redirectToRoute('app_prod_dashboard');
        }
        return $this->render('dashboard/prodedit.html.twig', [
            'ProdAdminController' => 'ProdAdminController',
            'form' => $form->createView(),
            'uinfo'=>$prodinfo
        ]);
    }
}
