<?php

namespace App\Form;
use App\Entity\Formation;
use App\Entity\Cours;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
class CoursType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder->setAttribute('novalidate','novalidate');
        $builder
            ->add('titreCours')
            ->add('dureeCours')
            ->add('formation', EntityType::class, [
                'class' => Formation::class,
                'choice_label' => 'titre', 
                'placeholder' => 'Sélectionnez une formation', 
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Cours::class,
        ]);
    }
}
