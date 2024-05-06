<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use SpotifyWebAPI\Session;
use Symfony\Component\Security\Guard\Token\PostAuthenticationGuardToken;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\HttpFoundation\Request;


class SpotifyController extends AbstractController
{


    #[Route("/spotify/authorize", name: "app_spotify_authorize")]

    public function authorize(SessionInterface $session): Response
    {
        $user = $this->getUser();
        dump($user);

        $session->set('user', $this->getUser());

        $spotifySession = new Session(
            '6b7edfd326df43deb87ff4da4cde34a4',
            'da79d69b18d04a51b75b6835cab3461d',
            'http://localhost:8000/item/marketplace'
        );

        $options = [
            'scope' => [
                'user-read-email',
                'user-read-private',
            ],
        ];

        return $this->redirect($spotifySession->getAuthorizeUrl($options));
    }


    #[Route("/spotify/callback", name: "app_spotify_callback")]

    public function handleRedirect(Request $request, SessionInterface $session, UserPasswordEncoderInterface $passwordEncoder): Response
    {
        $user = $session->get('user');

        $token = new PostAuthenticationGuardToken($user, 'main', $user->getRoles());
        $this->get('security.token_storage')->setToken($token);
        $this->get('session')->set('_security_main', serialize($token));

        $spotifySession = new Session(
            '6b7edfd326df43deb87ff4da4cde34a4',
            'da79d69b18d04a51b75b6835cab3461d',
            'http://localhost:8000/item/marketplace'
        );

        $spotifySession->requestAccessToken($request->get('code'));

        $accessToken = $spotifySession->getAccessToken();
        error_log('Spotify Access Token: ' . $accessToken);


        $session->set('spotify_access_token', $accessToken);
        $session->save();


        return $this->redirectToRoute('app_item_index');
    }
}
