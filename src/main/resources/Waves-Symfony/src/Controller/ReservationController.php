<?php

namespace App\Controller;

use App\Entity\Reservation;
use App\Form\ReservationType;
use App\Repository\EventRepository;
use App\Service\EmailService;
use App\Repository\ReservationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use Stripe\Stripe;
use Stripe\Charge;

    // Configuration des clés d'API Stripe
    Stripe::setApiKey(getenv('STRIPE_SECRET_KEY'));


class ReservationController extends AbstractController
{
    #[Route('/', name: 'app_reservation_index', methods: ['GET'])]
    public function index(ReservationRepository $reservationRepository): Response
    {
        return $this->render('reservation/index.html.twig', [
            'reservations' => $reservationRepository->findAll(),
        ]);
    }


   

    #[Route('/new/{eventId}', name: 'app_reservation_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager, EmailService $emailService, $eventId, MailerInterface $mailer): Response
    {
        $reservationId=null;
        $reservation = new Reservation();
        $eventid = $request->get("eventId");
        $reservation->setEid($eventid);  
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($reservation);
            $entityManager->flush();
    
            $reservationId = $reservation->getId();
    
            $emailService->sendReservationConfirmation($reservation->getEmail());
            
            $this->addFlash('success', 'Votre réservation a été ajoutée avec succès');
    
            return $this->redirectToRoute('app_reservation_new', ["eventId" => $reservationId ], Response::HTTP_SEE_OTHER);
        }
    
        return $this->renderForm('reservation/new.html.twig', [
            'reservation' => $reservation,
            'form' => $form,
            'eventId' => $eventid,
        ]);
    }
    
    
    
    
    


   // #[Route('/new1', name: 'app_reservation_new', methods: ['GET', 'POST'])]
    //public function new1(Request $request, EntityManagerInterface $entityManager): Response
    //{
      //  $reservation = new Reservation();
        //$form = $this->createForm(ReservationType::class, $reservation);
        //$form->handleRequest($request);

    /*    if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($reservation);
            $entityManager->flush();

            return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reservation/new.html.twig', [
            'reservation' => $reservation,
            'form' => $form,
     //   ]);
        }*/ 

    #[Route('/reservationshow/{id}', name: 'app_reservation_show', methods: ['GET'])]
    public function show(Reservation $reservation): Response
    {
        return $this->render('reservation/show.html.twig', [
            'reservation' => $reservation,
        ]);
    }

    #[Route('/reservationedit/{id}', name: 'app_reservation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Reservation $reservation, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reservation/edit.html.twig', [
            'reservation' => $reservation,
            'form' => $form,
        ]);
    }

    #[Route('/reservation/{id}', name: 'app_reservation_delete', methods: ['POST'])]
    public function delete(Request $request, Reservation $reservation, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reservation->getId(), $request->request->get('_token'))) {
            $entityManager->remove($reservation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
    }



    #[Route('/generatepdf/{id}', name: 'app_generer_pdf', methods: ['GET'])]
public function generatepdf(Request $request, ReservationRepository $repo, EventRepository $repoE): Response
{   
    $reservationId = $request->get('id');
    $reservation = $repo->find($reservationId);
    $r = $reservation->getEid();
    $event = $repoE->find($r);

    $pdf = new \TCPDF(PDF_PAGE_ORIENTATION, PDF_UNIT, PDF_PAGE_FORMAT, true, 'UTF-8', false);
    
    $pdf->AddPage();

     $backgroundImagePath = $this->getParameter('kernel.project_dir') . '/public/assets/img/backgrounds/bbk.jpg';
     $pdf->Image($backgroundImagePath, 0, 0, $pdf->getPageWidth(), $pdf->getPageHeight(), '', '', '', false, 300, '', false, false, 0);
     $logoPath = $this->getParameter('kernel.project_dir') . '/public/assets/img/images/Logo.png';
     $pdf->Image($logoPath, 10, 10, 15, '', 'PNG', '', 'T', false, 300, '', false, false, 0, false, false, false);
    $pdfContent = '
        <h1 style="text-align: center; color: #c388d5; font-size: 20pt;">Bienvenue chez nous !</h1>
        <p style="font-size: 12pt;">Merci d\'avoir choisi de réserver avec nous. Votre réservation pour l\'événement ' . $event->getNomE() .' a été confirmée avec succès.</p>
        <p style="font-size: 12pt;">Voici les détails de votre réservation :</p>
        <ul style="font-size: 12pt;">
            <li><strong>ID de la réservation :</strong> ' . $reservation->getId() . '</li>
            <li><strong>Nom :</strong> ' . $reservation->getNom() . '</li>
            <li><strong>Prénom :</strong> ' . $reservation->getPrenom() . '</li>
            <li><strong>Réservation pour :</strong> ' . $reservation->getNbPersonne() . ' personnes</li>
            <li><strong>Date de la réservation :</strong> ' . $reservation->getDateReservation() . ' (Svp ne dépassez pas l\'heure de la réservation)</li>
        </ul>
        <p style="text-align: center; font-size: 12pt;">Nous sommes impatients de vous accueillir !</p>
    ';
    

    $pdf->writeHTML($pdfContent);

    $outputFile = $this->getParameter('kernel.project_dir') . '/public/files/reservation.pdf';
    $pdf->Output($outputFile, 'F');

    return $this->file($outputFile, 'reservation.pdf', ResponseHeaderBag::DISPOSITION_INLINE);
}



public function processPayment(Request $request): Response
{
    // Montant fixe pour le paiement de test (par exemple, 10 EUR)
    $amount = 1000; // 10 EUR en centimes

    // Devise de la transaction (EUR)
    $currency = 'eur';

    // Token de carte de crédit (simulé pour les tests)
    $token = 'tok_visa'; // C'est un token de carte de crédit de test fourni par Stripe

    // Créer une charge avec les informations de paiement
    try {
        $charge = Charge::create([
            'amount' => $amount,
            'currency' => $currency,
            'source' => $token,
            'description' => 'Paiement de test pour un produit ou un service',
        ]);

        // Le paiement a réussi
        return new Response('Paiement de test réussi !');
    } catch (\Stripe\Exception\CardException $e) {
        // Le paiement a échoué, gérer les erreurs
        return new Response('Le paiement de test a échoué : ' . $e->getMessage());
    }
}
}