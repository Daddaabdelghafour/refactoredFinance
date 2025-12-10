package com.university.finance.exception;

import org.junit.Test;
import static org.junit.Assert.*;

public class BusinessExceptionTest {
    
    // Teste le constructeur par défaut de BusinessException
    @Test
    public void testBusinessExceptionDefault() {
        BusinessException exception = new BusinessException();
        assertNull(exception.getMessage());
        assertNull(exception.getErrorCode());
    }
    
    // Teste BusinessException avec un message d'erreur
    @Test
    public void testBusinessExceptionWithMessage() {
        BusinessException exception = new BusinessException("Test error");
        assertEquals("Test error", exception.getMessage());
        assertNull(exception.getErrorCode());
    }
    
    // Teste BusinessException avec message et code d'erreur
    @Test
    public void testBusinessExceptionWithErrorCode() {
        BusinessException exception = new BusinessException("Test error", "ERROR_CODE");
        assertEquals("Test error", exception.getMessage());
        assertEquals("ERROR_CODE", exception.getErrorCode());
    }
    
    // Teste BusinessException avec message et cause
    @Test
    public void testBusinessExceptionWithCause() {
        Throwable cause = new RuntimeException("Root cause");
        BusinessException exception = new BusinessException("Test error", cause);
        assertEquals("Test error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
    
    // Teste BusinessException avec message, code d'erreur et cause
    @Test
    public void testBusinessExceptionWithErrorCodeAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        BusinessException exception = new BusinessException("Test error", "ERROR_CODE", cause);
        assertEquals("Test error", exception.getMessage());
        assertEquals("ERROR_CODE", exception.getErrorCode());
        assertEquals(cause, exception.getCause());
    }
    
    // Teste le setter du code d'erreur
    @Test
    public void testBusinessExceptionSetErrorCode() {
        BusinessException exception = new BusinessException("Test error");
        exception.setErrorCode("NEW_CODE");
        assertEquals("NEW_CODE", exception.getErrorCode());
    }
    
    // Teste la représentation en chaîne de caractères de BusinessException
    @Test
    public void testBusinessExceptionToString() {
        BusinessException exception = new BusinessException("Test error", "ERROR_CODE");
        String str = exception.toString();
        assertTrue(str.contains("BusinessException"));
        assertTrue(str.contains("ERROR_CODE"));
        assertTrue(str.contains("Test error"));
    }
    
    // Teste InsufficientFundsException avec ses attributs
    @Test
    public void testInsufficientFundsException() {
        InsufficientFundsException exception = new InsufficientFundsException("A001", 100.0, 500.0);
        
        assertEquals("A001", exception.getAccountId());
        assertEquals(100.0, exception.getCurrentBalance(), 0.01);
        assertEquals(500.0, exception.getRequestedAmount(), 0.01);
        assertEquals(400.0, exception.getMissingAmount(), 0.01);
    }
    
    // Teste ValidationException avec message et nom de champ
    @Test
    public void testValidationException() {
        ValidationException exception = new ValidationException("Invalid data", "fieldName");
        
        assertEquals("Invalid data", exception.getMessage());
        assertEquals("fieldName", exception.getFieldName());
        assertEquals("VALIDATION_ERROR", exception.getErrorCode());
    }
    
    // Teste ValidationException avec message, champ et cause
    @Test
    public void testValidationExceptionWithCause() {
        Throwable cause = new RuntimeException("Root cause");
        ValidationException exception = new ValidationException("Invalid data", "fieldName", cause);
        
        assertEquals("Invalid data", exception.getMessage());
        assertEquals("fieldName", exception.getFieldName());
        assertEquals(cause, exception.getCause());
    }
    
    // Teste le setter du nom de champ dans ValidationException
    @Test
    public void testValidationExceptionSetFieldName() {
        ValidationException exception = new ValidationException("Invalid data");
        exception.setFieldName("newField");
        assertEquals("newField", exception.getFieldName());
    }
    
    // Teste la représentation en chaîne de caractères de ValidationException
    @Test
    public void testValidationExceptionToString() {
        ValidationException exception = new ValidationException("Invalid data", "fieldName");
        String str = exception.toString();
        assertTrue(str.contains("fieldName"));
    }
}

