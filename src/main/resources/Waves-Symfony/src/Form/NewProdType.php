<?php

namespace App\Form;

use App\Entity\Production;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
class NewProdType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom', TextType::class, [
                'label' => false,
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'emailBasic',
                ],
            ])
            ->add('description', TextareaType::class, [
                'label' => false,
                'attr' => [
                    'class' => 'form-control',
                ],
            ])
            ->add('genre', TextType::class, [
                'label' => false,
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'emailBasic',
                ],
            ])
            ->add('moodtag', ChoiceType::class, [
                'label' => false,
                'choices' => [
                    'Heureux' => 'Heureux',
                    'Calme' => 'Calme',
                    'Sentimental' => 'Sentimental',
                    'Agressif' => 'Agressif',
                    'Lourd' => 'Lourd',
                    'Sombre' => 'Sombre',
                    'Energitique' => 'Energitique',
                    'Motivation' => 'Motivation',
                ],
                'multiple' => false,
                'required' => false,
                'attr' => [
                    'class' => 'form-select',
                    'id' => 'verifySelect',
                ],
            ])
            ->add('cover', FileType::class, [
                'label' => false,
                'required' => false,
                'mapped' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'imageFile',
                ],
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Production::class,
        ]);
    }
}
