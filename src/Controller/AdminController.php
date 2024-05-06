<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\AdminUserEditType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;

class AdminController extends AbstractController
{
    #[Route('/userdashboard', name: 'app_user_dashboard')]
    public function index(UserRepository $userRepository): Response
    {
        return $this->render('dashboard/userdashboard.html.twig', [
            'user' => $userRepository->findAll(),
        ]);
    }
    #[Route('/userEditDash/{id}', name: 'app_userEdit_dashboard')]
    public function EditUser($id, UserRepository $userRepository, User $user, Request $request, UserPasswordHasherInterface $userPasswordHasher): Response
    {
        $userinfo = $userRepository->find($id);
        $form = $this->createForm(AdminUserEditType::class, $userinfo);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $password = $form->get('password')->getData();
            if (empty($password)) {
                $user->setPassword($userinfo->getPassword());
            } else {
                $userPasswordHasher->hashPassword(
                    $user,
                    $password
                );
            }
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($userinfo);
            $entityManager->flush();
            $this->addFlash('message', 'Utilisateur modifié avec succès');
            return $this->redirectToRoute('app_user_dashboard');
        }
        return $this->render('dashboard/useredit.html.twig', [
            'AdminController' => 'AdminController',
            'form' => $form->createView(),
            'uinfo' => $userinfo
        ]);
    }
    #[Route('/userdashboard/{id}', name: 'app_userSupp_dashboard')]
    public function SuppUser($id): Response
    {
        $user = $this->getDoctrine()->getRepository(User::class)->find($id);
        $em = $this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();
        return $this->redirectToRoute('app_user_dashboard');
    }
}
