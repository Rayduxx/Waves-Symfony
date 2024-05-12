<?php


namespace App\Controller;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use League\OAuth2\Client\Provider\Facebook;
use App\Entity\User;
use App\Repository\UserRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\PasswordHasher\Hasher\UserPasswordHasherInterface;
use App\Security\EmailVerifier;
use App\Security\LoginFormAuthenticator;
use Symfony\Component\Security\Guard\GuardAuthenticatorHandler;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Component\Mime\Address;

class AppController extends AbstractController
{
    private $provider;
    public function __construct()
    {
        $this->provider = new Facebook([
            'clientId'          => $_ENV['FCB_ID'],
            'clientSecret'      => $_ENV['FCB_SECRET'],
            'redirectUri'       => $_ENV['FCB_CALLBACK'],
            'graphApiVersion'   => 'v19.0',
        ]);
    }
    #[Route('/redbyfcb', name: 'fcb_login')]
    public function fcbLogin(): Response
    {
        /* return $this->render('index.html.twig', [
            'user' => $this->getUser()
        ]);*/
        $helper_url = $this->provider->getAuthorizationUrl();
        return $this->redirect($helper_url);
    }
    #[Route('/fcb-callback', name: 'fcb_callback')]
    public function fcbCallBack(EmailVerifier $emailVerifier, UserRepository $userDb, Request $request,UserPasswordHasherInterface $userPasswordHasher, GuardAuthenticatorHandler $guardHandler, LoginFormAuthenticator $authenticator, EntityManagerInterface $manager): Response
    {
        $token = $this->provider->getAccessToken('authorization_code', ['code' => $_GET['code']]);
        try {
            $user = $this->provider->getResourceOwner($token);
            $user = $user->toArray();
            $email = $user['email'];
            $name = $user['first_name'];
            $prename = $user['last_name'];
            $picture = $user['picture_url'];
            $imageContent = file_get_contents($picture);    
            $filename = md5(uniqid()) . '.png';
            $destinationFolder = $this->getParameter('images_directory');
            $localImagePath = $destinationFolder . '/' . $filename;
            file_put_contents($localImagePath, $imageContent);
            $user_exist = $userDb->findOneByEmail($email);
            if ($user_exist) {
                $user = $user_exist;
                $this->addFlash('success', 'Logged in successfully.');
                return $guardHandler->authenticateUserAndHandleSuccess($user, $request, $authenticator, 'main');
            } else {
                $new_user = new User();
                $new_user->setName($name)
                    ->setPrename($prename)
                    ->setEmail($email)
                    ->setRoles(array("ROLE_USER"))
                    ->setPassword($userPasswordHasher->hashPassword($new_user, $name . $prename . 1))
                    ->setImage($filename);
                    $this->$emailVerifier->sendEmailConfirmation(
                        'app_verify_email',
                        $user,
                        (new TemplatedEmail())
                            ->from(new Address('admin@security-demo.com', 'Security'))
                            ->to($new_user->getEmail())
                            ->subject('Please Confirm your Email')
                            ->htmlTemplate('security/register-done.html.twig')
                    );
                $this->addFlash('info', 'Votre mot de passe : ' . $userPasswordHasher->hashPassword($new_user, $name . $prename . 1));
                $manager->persist($new_user);
                $manager->flush();
                return $this->redirectToRoute('app_login');
            }
        } catch (\Throwable $th) {
            throw $th;
        }
    }




    #[Route('/', name: 'app_homepage')]
    public function index(): Response
    {
        return $this->render('index.html.twig', [
            'user' => $this->getUser()
        ]);
    }
    #[Route('/download/waves', name: 'download_waves')]
    public function downloadWavesFile(): Response
    {
        $filePath = $this->getParameter('kernel.project_dir') . '/public/files/Waves.exe';
        if (!file_exists($filePath)) {
            throw $this->createNotFoundException('The file does not exist');
        }
        $response = new BinaryFileResponse($filePath);
        $response->headers->set('Content-Type', 'application/octet-stream');
        $response->headers->set('Content-Disposition', $response->headers->makeDisposition(ResponseHeaderBag::DISPOSITION_ATTACHMENT, 'Waves.exe'));
        return $response;
    }
}
