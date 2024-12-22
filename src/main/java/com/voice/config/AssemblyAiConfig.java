package com.voice.config;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.RealtimeTranscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssemblyAiConfig {

    @Value("${assembly.ai.api-key}")
    private String apiKey;

    @Bean
    public AssemblyAI assemblyAI(){
        return  AssemblyAI.builder()
                .apiKey(apiKey)
                .build();
    }

}
