<?php

namespace App\Form;
use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;

class AdminUserEditType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('id', TextType::class, [
                'required' => false,
                'label' => false,
                'disabled' => true,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'uid',
                ],
            ])
            ->add('email', TextType::class, [
                'required' => false,
                'label' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'emailBasic',
                ],
            ])
            ->add('name', TextType::class, [
                'required' => false,
                'label' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'nameBasic',
                ],
            ])
            ->add('prename', TextType::class, [
                'required' => false,
                'label' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'prenameBasic',
                ],
            ])
            ->add('password', TextType::class, [
                'required' => false,
                'label' => false,
                'attr' => [
                    'placeholder' => 'Nouveau mot de passe',
                    'class' => 'form-control',
                    'id' => 'mdpBasic',
                ],
            ])
            ->add('phone', TextType::class, [
                'required' => false,
                'label' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'phoneBasic',
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
            ])
            ->add('phone', TextType::class, [
                'required' => false,
                'label' => false,
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'phoneBasic',
                ],
            ])
            ->add('isVerified', ChoiceType::class, [
                'choices'  => [
                    'Vérifié' => '1',
                    'Non vérifié' => '0',
                ],
                'multiple' => false,
                'required' => false,
                'label' => false,
                'attr' => [
                    'class' => 'form-select',
                    'id' => 'verifySelect',
                ],
            ])
            ->add('roles', ChoiceType::class, [
                'attr' => [
                    'class' => 'form-check-input',
                    'type' => 'checkbox',
                    'id' => 'inlineCheckbox2',
                ],
                'choices' => [
                    'User' => 'ROLE_USER',
                    'Admin' => 'ROLE_ADMIN',
                ],
                'expanded' => true,
                'multiple' => true,
                'label' => false,
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
