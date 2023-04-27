package com.iiht.fse4.skilltrackersearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler( value = AssociateNotfoundException.class)
    public ResponseEntity<Object> handleAssociateNotFoundException(AssociateNotfoundException exception) {
        return new ResponseEntity<>("Associate not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler( value = AssociateNotfoundForGivenSkillException.class)
    public ResponseEntity<Object> handleAssociateNotFoundForSkillException(AssociateNotfoundForGivenSkillException exception) {
        return new ResponseEntity<>("Associate not found for the given Skillset", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler( value = MongoDBRepoSaveException.class)
    public ResponseEntity<Object> handleMongoDBRepoSaveException(MongoDBRepoSaveException exception) {
        return new ResponseEntity<>("Associate data could not be saved to MongoDB", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> requestHandlingNoHandlerFound() {
        //return new ErrorResponse("custom_404", "message for 404 error code");
        return new ResponseEntity<>("Wrong URL invoked", HttpStatus.NOT_FOUND);
    }



}
