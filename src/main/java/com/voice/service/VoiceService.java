package com.voice.service;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.files.types.UploadedFile;
import com.assemblyai.api.resources.transcripts.requests.TranscriptParams;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@Service
public class VoiceService {

    @Autowired
    private AssemblyAI client;

    public String voiceToText(MultipartFile file) throws Exception{
        File tempFile = File.createTempFile("file",file.getName());
        file.transferTo(tempFile);
        try{
            UploadedFile upload = client.files().upload(tempFile);
            String uploadUrl = upload.getUploadUrl();

            TranscriptParams transcriptParams = TranscriptParams.builder()
                    .audioUrl(uploadUrl)
                    .speakerLabels(true)
                    .build();
            Transcript transcript = client.transcripts().transcribe(transcriptParams);
            if (transcript.getStatus() == TranscriptStatus.ERROR) {
                throw new Exception("Transcript failed with error: " + transcript.getError().get());
            }
            Optional<String> text = transcript.getText();
            if (text.isPresent()){
                return text.get();
            }else {
                throw new RuntimeException();
            }
        }catch (Exception e){
            throw new RuntimeException();
        }finally {
            tempFile.deleteOnExit();
        }
    }
}
