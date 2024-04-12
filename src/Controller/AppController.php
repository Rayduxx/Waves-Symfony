<?php


namespace App\Controller;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
class AppController extends AbstractController
{
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
        // Path to your Waves.exe file
        $filePath = $this->getParameter('kernel.project_dir') . '/public/files/Waves.exe';

        // Check if the file exists
        if (!file_exists($filePath)) {
            throw $this->createNotFoundException('The file does not exist');
        }

        // Create a BinaryFileResponse object
        $response = new BinaryFileResponse($filePath);

        // Set the headers to force download the file
        $response->headers->set('Content-Type', 'application/octet-stream');
        $response->headers->set('Content-Disposition', $response->headers->makeDisposition(
            ResponseHeaderBag::DISPOSITION_ATTACHMENT,
            'Waves.exe'
        ));

        return $response;
    }
}