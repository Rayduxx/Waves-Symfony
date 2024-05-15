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
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Validator\Constraints\Type;

class RegisterType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('image', FileType::class, [
                'label' => 'Photo de profile:',
                'mapped' => false,
                'required' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'formFile',
                ],
            ])
            ->add('name', null, [
                'label' => 'Nom',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Votre nom',
                    'id' => 'nameBasic'
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez entrer votre Nom',
                    ]),
                    new Length([
                        'min' => 2,
                        'minMessage' => 'Votre nom doit etre au moins {{ limit }} caractères',
                        'max' => 10,
                    ]),
                ]
            ])
            ->add('prename', null, [
                'label' => 'Prenom',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Votre prenom',
                    'id' => 'nameBasic',
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez entrer votre Prenom',
                    ]),
                    new Length([
                        'min' => 2,
                        'minMessage' => 'Votre prenom doit etre au moins {{ limit }} caractères',
                        'max' => 10,
                    ]),
                ]
            ])
            ->add('email', EmailType::class, [
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
                'constraints' => [
                    new Type('numeric'),
                    new Length(['min' => 8]),
                ]
            ])
            ->add('birthday', DateType::class, [
                'label' => 'Date de naissance',
                'html5' => true,
                'widget' => 'single_text',
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'html5-date-input'
                ],
                'constraints' => [new NotBlank([
                    'message' => 'Veuillez entrer votre Date de naissance',
                ])]
            ])
            ->add('password', PasswordType::class, [
                'label' => 'Mot de passe',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Creer mot de passe',
                    'id' => 'password'
                ],
                'constraints' => [
                    new NotBlank([
                        'message' => 'Veuillez entrer votre Mot de passe',
                    ]),
                    new Length([
                        'min' => 6,
                        'minMessage' => 'Your password should be at least {{ limit }} characters',
                        'max' => 4096,
                    ]),
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