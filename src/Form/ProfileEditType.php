<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Validator\Constraints\File;

class ProfileEditType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('image', FileType::class, [
                'mapped' => false,
                'required' => false,
                'label' => 'Photo de profile :',
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'formFile',
                ],
                'constraints' => [
                    new File([
                        'maxSize' => '40M',
                        'mimeTypes' => [
                            'image/*',"image/jpeg" , "image/png" , "image/tiff" , "image/svg+xml", "image/gif", "image/webp",
                        ],
                        'mimeTypesMessage' => 'Please upload a valid image file',
                    ])
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
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
