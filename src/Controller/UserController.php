<?php

namespace App\Controller;

use App\Entity\User;
use App\Security\EmailVerifier;
use App\Form\RegisterType;
use App\Form\ProfileEditType;
use App\Repository\UserRepository;
use App\Security\LoginFormAuthenticator;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mime\Address;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use Symfony\Component\Security\Guard\GuardAuthenticatorHandler;
use SymfonyCasts\Bundle\VerifyEmail\Exception\VerifyEmailExceptionInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\HttpFoundation\IpUtils;

class UserController extends AbstractController
{

    private $emailVerifier;
    /**
     * @var Security
     */
    private $security;
    public function __construct(EmailVerifier $emailVerifier, Security $security)
    {
        $this->emailVerifier = $emailVerifier;
        $this->security = $security;
    }

    #[Route('/profile', name: 'app_profile')]
    public function profile()
    {
        return $this->render('user/profile.html.twig');
    }
    
    #[Route('/edit-profile', name: 'app_edit_profile')]
    public function editProfile(Request $request, UserPasswordHasherInterface $userPasswordHasher, User $user): Response
    {
        $userinfo =  $this->getUser();
        $form = $this->createForm(ProfileEditType::class, $userinfo);
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
            $imageFile = $form['image']->getData();
            if ($imageFile) {
                try {
                    $destinationFolder = $this->getParameter('images_directory');
                    $filename = md5(uniqid()) . '.' . $imageFile->guessExtension();
                    $imageFile->move($destinationFolder, $filename);
                    $userinfo->setImage($filename);
                    $this->addFlash('success', 'Profile updated successfully');
                } catch (\Exception $e) {
                    $this->addFlash('error', 'Error uploading image: ' . $e->getMessage());
                }
            }
            $this->addFlash('success', 'Profile updated successfully');
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->flush();
            dump($imageFile);
            return $this->redirectToRoute('app_profile');
        }
        return $this->render('user/profileEdit.html.twig', [
            'userinfo' => $userinfo,
            'form' => $form->createView()
        ]);
    }

    #[Route('/register', name: 'app_register')]
    public function register(Request $request, UserPasswordHasherInterface $userPasswordHasher, LoginFormAuthenticator $authenticator, EntityManagerInterface $entityManager, GuardAuthenticatorHandler $guardHandler): Response
    {
        $user = new User();
        $form = $this->createForm(RegisterType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form['image']->getData();
            if ($imageFile) {
                $filename = md5(uniqid()) . '.' . $imageFile->guessExtension();
                $imageFile->move($this->getParameter('images_directory'), $filename);
                $user->setImage($filename);
            } else {
                $user->setImage("default.png");
            }
            $user->setPassword($userPasswordHasher->hashPassword($user, $form->get('password')->getData()));
            /*$plainPassword = $form->get('password')->getData();
            $hashedPassword = hash('sha256', $plainPassword);
            $user->setPassword($hashedPassword);*/
            $user->setRoles(array("ROLE_USER"));
            $user->setCountry("Tunisia");
            $user->setCreatedAt(new \DateTimeImmutable());
            $entityManager->persist($user);
            $entityManager->flush();
            $this->emailVerifier->sendEmailConfirmation(
                'app_verify_email',
                $user,
                (new TemplatedEmail())
                    ->from(new Address('admin@security-demo.com', 'Security'))
                    ->to($user->getEmail())
                    ->subject('Please Confirm your Email')
                    ->htmlTemplate('security/register-done.html.twig')
            );
            return $this->redirectToRoute('app_login');
            //return $guardHandler->authenticateUserAndHandleSuccess($user, $request, $authenticator, 'main');
        }
        return $this->render('security/register.html.twig', ['form' => $form->createView(),]);
    }

    #[Route('/verify/email', name: 'app_verify_email')]    
    public function verifyUserEmail(Request $request): Response
    {
        $this->denyAccessUnlessGranted('IS_AUTHENTICATED_FULLY');
        try {
            $this->emailVerifier->handleEmailConfirmation($request, $this->getUser());
        } catch (VerifyEmailExceptionInterface $exception) {
            $this->addFlash('verify_email_error', $exception->getReason());
            return $this->redirectToRoute('app_register');
        }
        $this->addFlash('success', 'Your email address has been verified.');
        return $this->redirectToRoute('app_homepage');
    }

    #[Route('/login', name: 'app_login')]
    public function login(AuthenticationUtils $authenticationUtils): Response
    {
        $error = $authenticationUtils->getLastAuthenticationError();
        $lastUsername = $authenticationUtils->getLastUsername();
        return $this->render('security/login.html.twig', ['last_username' => $lastUsername, 'error' => $error]);
    }

    #[Route('/logout', name: 'app_logout')]
    public function logout()
    {
        throw new \LogicException('This method can be blank - it will be intercepted by the logout key on your firewall.');
    }

    #[Route('/users', name: 'app_user_list')]
    public function UsersList(UserRepository $userRepository)
    {
        return $this->render(
            'user/listusers.html.twig',[
                'users' => $userRepository->findAll(),
            ]);
    }

    #[Route('/userprofile/{id}', name: 'app_userprofile')]
    public function UserProfile($id, UserRepository $userRepository)
    {
        $UserDetails = $userRepository->find($id);
        
        return $this->render('user/UserProfile.html.twig', [
            'UserController' => 'UserController',
            'UserDetail' => $UserDetails,
        ]);
    }
}
