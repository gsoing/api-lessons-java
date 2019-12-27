# Commentaires
Bon TP, bonne organisation du code

Pour tes exceptions fonctionnelles tu aurais pu ajouter leur gestion au RestControllerAdvice comme cela, cela évite
d'avoir des gestions d'erreurs réparties dans le code
```java
    @ExceptionHandler({NotFoundException.class, PreconditionFailedException.class})
    public final ResponseEntity<ErrorDto> handleNotFound(AbstractException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(
                        ErrorDto
                                .builder()
                                .errorCode(ex.getErrorCode())
                                .errorMessage(ex.getErrorMessage())
                                .build()
                );
    }
```