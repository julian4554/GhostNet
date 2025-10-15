package com.shea.ghostnets.web.dto;
import lombok.*;


/**
 * AssignSalvagerRequest
 * Datenobjekt, das vom Frontend gesendet wird,
 * wenn eine Person ein Geisternetz zur Bergung übernimmt
 * sinn:
 *  Enthält minimale Personendaten (Name + Telefonnummer)
 *  Wird vom Controller entgegengenommen und an den Service weitergereicht
 *  Validiert automatisch über Bean Validation
 */
@Getter
@Setter
public class AssignSalvagerRequest {
     private String name;
     private String phone;
}
