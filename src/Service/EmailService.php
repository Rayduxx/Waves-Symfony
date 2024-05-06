<?php

namespace App\Service;

use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

class EmailService
{
    private $mailer;

    public function __construct(MailerInterface $mailer)
    {
        $this->mailer = $mailer;
    }

    public function sendReservationConfirmation(string $recipientEmail)
    {
        $emailContent = "
    Bonjour,
    <br><br>
    Votre réservation a été confirmée avec succès !
    <br><br>
    Nous avons hâte de vous accueillir !
    <br><br>
    Cordialement,<br>
    Votre équipe de réservation
";

    
        $email = (new Email())
            ->from('ahmeddhouioui29@gmail.com')
            ->to($recipientEmail)
            ->subject('Confirmation de réservation')
            ->html($emailContent);
    
        $this->mailer->send($email);
    }
    
}
