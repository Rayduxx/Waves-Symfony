<?php

namespace App\Controller;
use App\Entity\Formation;
use App\Repository\FormationRepository;
use App\Repository\PosteRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;

class PdfController extends AbstractController
{
    #[Route('/listS', name: 'generate_formations_pdf')]
    public function pdf(FormationRepository $formationRepository): Response
    {
       // Configure Dompdf according to your needs
       $pdfOptions = new Options();
       $pdfOptions->set('defaultFont', 'Open Sans');

       // Instantiate Dompdf with our options
       $dompdf = new Dompdf($pdfOptions);
       // Retrieve the HTML generated in our twig file
       $html = $this->renderView('formation/print.html.twig', [
           'formations' => $formationRepository->findAll(),
       ]);

       // Add header HTML to $html variable
       $headerHtml = '<h1 style="text-align: center; color: #b00707;">Liste des formations</h1>';
       $html = $headerHtml . $html;

       // Load HTML to Dompdf
       $dompdf->loadHtml($html);
       // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
       $dompdf->setPaper('A3', 'portrait');

       // Render the HTML as PDF
       $dompdf->render();
       
       // Output the generated PDF to Browser (inline view)
       return new Response($dompdf->output(), Response::HTTP_OK, [
           'Content-Type' => 'application/pdf',
       ]);
    }
    #[Route('/list', name: 'pdf_export')]
    public function pdf2(PosteRepository $posteRepository): Response
    {
       // Configure Dompdf according to your needs
       $pdfOptions = new Options();
       $pdfOptions->set('defaultFont', 'Open Sans');

       // Instantiate Dompdf with our options
       $dompdf = new Dompdf($pdfOptions);
       // Retrieve the HTML generated in our twig file
       $html = $this->renderView('poste/print.html.twig', [
           'postes' => $posteRepository->findAll(),
       ]);

       // Add header HTML to $html variable
       $headerHtml = '<h1 style="text-align: center; color: #b00707;">Liste des postes</h1>';
       $html = $headerHtml . $html;

       // Load HTML to Dompdf
       $dompdf->loadHtml($html);
       // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
       $dompdf->setPaper('A3', 'portrait');

       // Render the HTML as PDF
       $dompdf->render();
       
       // Output the generated PDF to Browser (inline view)
       return new Response($dompdf->output(), Response::HTTP_OK, [
           'Content-Type' => 'application/pdf',
       ]);
    }
}