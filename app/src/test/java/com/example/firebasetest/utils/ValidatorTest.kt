package com.example.firebasetest.utils

import junit.framework.TestCase
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

}