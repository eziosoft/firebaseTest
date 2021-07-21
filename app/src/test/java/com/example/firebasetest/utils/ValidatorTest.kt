package com.example.firebasetest.utils

import org.junit.Assert
import org.junit.Test

class ValidatorTest
{
    private val validator = Validator()

    // Email
    @Test
    fun check_email_success_validation() {
        val email = "sadasdqw.fsfd@gmail.com"

        Assert.assertEquals(true, validator.isEmailValid(email))
    }

    @Test
    fun check_email_success_validation_specialChar() {
        val email = "regerg.egeg+hrhr@gmail.com"

        Assert.assertEquals(true, validator.isEmailValid(email))
    }

    @Test
    fun check_empty_email_fail_validation() {
        val email = ""

        Assert.assertEquals(false, validator.isEmailValid(email))
    }

    @Test
    fun check_bad_email_fail_validation() {
        val email = "email"

        Assert.assertEquals(false, validator.isEmailValid(email))
    }


    // Password
    @Test
    fun check_password_success_validation() {
        val password = "Qwerty1!"

        Assert.assertEquals(true, validator.isPasswordValid(password))
    }

    @Test
    fun check_empty_password_fail_validation() {
        val password = ""

        Assert.assertEquals(false, validator.isPasswordValid(password))
    }

    @Test
    fun check_no_number_password_fail_validation() {
        val password = "Qwerty!"

        Assert.assertEquals(false, validator.isPasswordValid(password))
    }

    @Test
    fun check_no_big_letter_password_fail_validation() {
        val password = "qwerty1!"

        Assert.assertEquals(false, validator.isPasswordValid(password))
    }

    @Test
    fun check_not_enough_characters_password_fail_validation() {
        val password = "Qwert1!"

        Assert.assertEquals(false, validator.isPasswordValid(password))
    }
}