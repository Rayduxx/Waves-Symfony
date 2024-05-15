<?php
namespace App\Security;

use Symfony\Component\PasswordHasher\Exception\InvalidPasswordException;
use Symfony\Component\PasswordHasher\Hasher\CheckPasswordLengthTrait;
use Symfony\Component\PasswordHasher\PasswordHasherInterface;
use RuntimeException;

class PasswordHasher implements PasswordHasherInterface
{
    use CheckPasswordLengthTrait;
    public function hash(string $plainPassword): string
    {
        if ($this->isPasswordTooLong($plainPassword)) {
            throw new InvalidPasswordException();
        }
        return hash('sha256', $plainPassword);
    }

    public function verify(string $hashedPassword, string $plainPassword): bool
    {
        if ('' === $plainPassword || $this->isPasswordTooLong($plainPassword)) {
            return false;
        }
        return $hashedPassword === $this->hash($plainPassword);
    }

    public function needsRehash(string $hashedPassword): bool
    {
        return false;
    }
}