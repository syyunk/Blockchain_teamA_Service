package com.ateam.popserver.common.dto;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDTO {
    private boolean isSuccess;
    private String error;

    private String uuid;
    private String fileName;
    private String realUploadPath;
}
