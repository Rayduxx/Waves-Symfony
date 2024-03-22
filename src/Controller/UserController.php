<?php

namespace App\Controller;

use App\Entity\User;
use App\Security\EmailVerifier;
use App\Form\RegisterType;
use App\Form\ProfileEditType;
use App\Form\User\EditProfileType;
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
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Vich\UploaderBundle\FileAbstraction\ReplacingFile;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\Validator\Constraints\NotNull;
use Symfony\Component\VarDumper\Cloner\Data;

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

    #[Route('/{id}/edit-profile', name: 'app_edit_profile')]
    public function editProfile(Request $request, User $user, $id): Response
    {
        $em = $this->getDoctrine()->getManager();
        $form = $this->createForm(ProfileEditType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

            $PofilePicFile = $form->get('image')->getData();
            if ($PofilePicFile) {
                $originalFilename = pathinfo($PofilePicFile->getClientOriginalName(), PATHINFO_FILENAME);
                $safeFilename = transliterator_transliterate('Any-Latin; Latin-ASCII; [^A-Za-z0-9_] remove; Lower()', $originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$PofilePicFile->guessExtension();
                try {
                    $PofilePicFile->move($this->getParameter('images_directory'),$newFilename);
                } catch (FileException $e) {
                }
                $user->setImage($newFilename);
            }
            $em->persist($user);
            $em->flush();
            return $this->redirectToRoute('app_profile', [
                'id' => $user->getId(),
            ]);
        }
        return $this->render('user/profileEdit.html.twig', [
            'user' => $user,
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
            $user->setPassword($userPasswordHasher->hashPassword($user,$form->get('password')->getData()));
            $randomImageNumber = rand(1, 20);
            $randomImageFilename = $randomImageNumber . '.png';
            $user->setImage($randomImageFilename);
            $user->setRoles(array("ROLE_USER"));
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
            return $guardHandler->authenticateUserAndHandleSuccess($user, $request, $authenticator, 'main');
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
}
