<?php

namespace App\Controller;

use App\Entity\Event;
use App\Form\EventType;
use App\Repository\EventRepository;
use App\Repository\ReservationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/event')]
class EventController extends AbstractController
{
    #[Route('/', name: 'app_event_index', methods: ['GET'])]
    public function index(EventRepository $eventRepository, ReservationRepository $reservationRepository): Response
    {
        $reservationCountByEvent = $reservationRepository->countReservationsByEvent();
        $chartData = [];
        foreach ($reservationCountByEvent as $result) {
            $eventId = $result['eventId'];
            $reservationCount = $result['reservationCount'];
            $chartData[$eventId] = $reservationCount;
        }
        return $this->render('dashboard/eventdashboard.html.twig', [
            'events' => $eventRepository->findAll(),
            'chartData' => $chartData,
        ]);
    }
    #[Route('/cards', name: 'app_event_card')]
    public function indexcard(EventRepository $eventRepository): Response
    {
        return $this->render('event/card.html.twig', [
            'events' => $eventRepository->findAll(),
        ]);
    }
    #[Route('/new', name: 'app_event_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $event = new Event();
        $form = $this->createForm(EventType::class, $event);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form['image']->getData();
            try {
                $destinationFolder = $this->getParameter('images_directory');
                $filename = md5(uniqid()) . '.' . $imageFile->guessExtension();
                $imageFile->move($destinationFolder, $filename);
                $event->setImage($filename);
            } catch (\Exception $e) {
                $this->addFlash('error', 'Error uploading image: ' . $e->getMessage());
            }
            $entityManager->persist($event);
            $entityManager->flush();
            return $this->redirectToRoute('app_event_index');
        }
        return $this->renderForm('dashboard/EventNew.html.twig', [
            'event' => $event,
            'form' => $form
        ]);
    }

    #[Route('/{id}', name: 'app_event_show')]
    public function show($id, EventRepository $eventRepository): Response
    {
        $event = $eventRepository->find($id);
        return $this->render('dashboard/Eventshow.html.twig', [
            'event' => $event,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_event_edit')]
    public function edit($id, Request $request, Event $event, EntityManagerInterface $entityManager, EventRepository $eventRepository): Response
    {
        $event = $eventRepository->find($id);  
        $form = $this->createForm(EventType::class, $event);
        $form->handleRequest($request);
        
        if ($form->isSubmitted() && $form->isValid()) {
            $imageFile = $form['image']->getData();
            if ($imageFile) {
                try {
                    $destinationFolder = $this->getParameter('images_directory');
                    $filename = md5(uniqid()) . '.' . $imageFile->guessExtension();
                    $imageFile->move($destinationFolder, $filename);
                    $event->setImage($filename);
                } catch (\Exception $e) {
                    $this->addFlash('error', 'Error uploading image: ' . $e->getMessage());
                }
            }
            $entityManager->flush();
            return $this->redirectToRoute('app_event_index');
        }

        return $this->renderForm('dashboard/edit.html.twig', [
            'event' => $event,
            'form' => $form,
        ]);
    }

    #[Route('/delete/{id}', name: 'app_event_delete')]
    public function delete($id): Response
    {
        $event = $this->getDoctrine()->getRepository(event::class)->find($id);
        $em = $this->getDoctrine()->getManager();
        $em->remove($event);
        $em->flush();
        return $this->redirectToRoute('app_event_index');
    }

    #[Route('/rechercheAjax', name: 'rechercheAjax', methods: ['GET'])]
    public function searchAjax(Request $request, EventRepository $repo)
    {
        $query = $request->query->get('q');
        $events = $repo->findEventByName($query);
        $html = $this->renderView(
            "event/index.html.twig",
            [
                "events" => $events,
            ]
        );
        return new Response($html);
    }
}
