package com.den;

import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfig {
  private static final String dateFormat = "dd-MM-yyyy";
  private static final String dateTimeFormat = "dd-MM-yyyy HH:mm:ss";
  @Value("${den.cloudinary.secret}")
  String apiSecret;
  @Value("${den.cloudinary.apiKey}")
  String apiKey;


//  @Bean
//  public SqlFileRepository sqlFileRepository() {
//    return new NoCacheSqlFileRepository();
//  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT);
    return modelMapper;
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> {
      builder.simpleDateFormat(dateTimeFormat);
      builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
      builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
    };
  }

  @Bean
  public Cloudinary getCloudinary() {
    Map<String, Object> config = new HashMap<>();
    config.put("cloud_name", "anhdaden");
    config.put("api_key", apiKey);
    config.put("api_secret", apiSecret);
    config.put("secure", true);
    return new Cloudinary(config);
  }


  @Bean
  public SimpleDateFormat getSimpleDateFormat() {
    return new SimpleDateFormat("dd/MM/yyyy");
  }

}
