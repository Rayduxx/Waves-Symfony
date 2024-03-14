<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Form\Extension\Core\Type\TelType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints\File;
class RegisterType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        /*->add('ProfilePicture', FileType::class, [
            'mapped' => false,
            'required' => false,
            'label' => 'Profile picture :',
            'attr' => [
                'class' => 'form-control',
                'id' => 'formFile',
            ],
            'constraints' => [
                new File([
                    'maxSize' => '1024k',
                    'mimeTypes' => [
                        'image/*',"image/jpeg" , "image/png" , "image/tiff" , "image/svg+xml", "image/gif", "image/webp",
                    ],
                    'mimeTypesMessage' => 'Please upload a valid image file',
                ])
            ],
        ])*/
            ->add('name', null, [
                'label' => 'Nom',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Votre nom',
                    'id' => 'nameBasic'
                ],
                'constraints' => [new NotBlank()]
                ])
            ->add('prename', null, [
                'label' => 'Prenom',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Votre prenom',
                    'id' => 'nameBasic',
                ],
                'constraints' => [new NotBlank()]
                ])
            ->add('email', null, [
                'label' => 'Email',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Votre email',
                    'id' => 'email',
                ],
                'constraints' => [new NotBlank()]
                ])
            ->add('phone', TelType::class, [
                'label' => 'N° Telephone',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => '+216 ',
                    'id' => 'tel',
                ],
                'constraints' => [new NotBlank()]
                ])
            ->add('birthday', DateType::class, [
                'label' => 'Date de naissance',
                'html5' => true,
                'widget' => 'single_text',
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'html5-date-input'
                ],
                'constraints' => [new NotBlank()]
                ])
            ->add('password', PasswordType::class, [
                'label' => 'Mot de passe',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Creer mot de passe',
                    'id' => 'password'
                ],
                'constraints' => [new NotBlank()]
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
