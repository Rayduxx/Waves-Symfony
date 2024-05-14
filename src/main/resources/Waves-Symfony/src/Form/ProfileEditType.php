<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\FileType;

class ProfileEditType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('image', FileType::class, [
                'label' => 'Image de profil :',
                'required' => false,
                'mapped' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'imageFile',
                ],
            ])
            ->add('name', TextType::class, [
                'label' => 'Nom :',
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'nameBasic',
                ],
            ])
            ->add('prename', TextType::class, [
                'label' => 'Prenom :',
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'nameBasic',
                ],
            ])
            ->add('phone', TextType::class, [
                'label' => 'N° Telephone :',
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'phone',
                ],
            ])
            ->add('email', TextType::class, [
                'label' => 'Email :',
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'email',
                ],
            ])
            ->add('password', TextType::class, [
                'label' => 'Mot de passe :',
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'password',
                ],
            ])
            ->add('birthday', DateType::class, [
                'required' => false,
                'label' => false,
                'html5' => true,
                'widget' => 'single_text',
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'html5-date-input'
                ],
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
