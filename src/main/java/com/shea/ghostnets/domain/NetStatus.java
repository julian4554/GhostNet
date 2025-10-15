package com.shea.ghostnets.domain;

/** Aufgabenstellung:
Der Status eines Geisternetzes kann folgende Ausprägungen haben:
− Gemeldet (Eine meldende Person hat das Geisternetz im System erfasst.)
− Bergung bevorstehend (Eine bergende Person hat die Bergung angekündigt.)
− Geborgen (Eine bergende Person hat das Geisternetz erfolgreich geborgen.)
− Verschollen (Eine beliebige Person hat festgestellt, dass das Geisternetz am gemeldeten Standort nicht auffindbar ist.)*/
public enum NetStatus {
    REPORTED, SALVAGE_PLANNED, RESCUED, LOST
}
