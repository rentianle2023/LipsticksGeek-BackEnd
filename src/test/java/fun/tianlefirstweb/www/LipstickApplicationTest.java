package fun.tianlefirstweb.www;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInitializer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

public class LipstickApplicationTest {
    @Test
    public void testRestTemplate() {
        LipstickApplication lipstickApplication = new LipstickApplication();
        MockServerRestTemplateCustomizer mockServerRestTemplateCustomizer = new MockServerRestTemplateCustomizer();
        MockServerRestTemplateCustomizer mockServerRestTemplateCustomizer1 = new MockServerRestTemplateCustomizer();
        RestTemplate actualRestTemplateResult = lipstickApplication.restTemplate(new RestTemplateBuilder(
                mockServerRestTemplateCustomizer, mockServerRestTemplateCustomizer1, new MockServerRestTemplateCustomizer()));
        List<ClientHttpRequestInitializer> clientHttpRequestInitializers = actualRestTemplateResult
                .getClientHttpRequestInitializers();
        assertTrue(clientHttpRequestInitializers.isEmpty());
        UriTemplateHandler uriTemplateHandler = actualRestTemplateResult.getUriTemplateHandler();
        assertTrue(uriTemplateHandler instanceof DefaultUriBuilderFactory);
        List<HttpMessageConverter<?>> messageConverters = actualRestTemplateResult.getMessageConverters();
        assertEquals(9, messageConverters.size());
        assertTrue(actualRestTemplateResult
                .getErrorHandler() instanceof org.springframework.web.client.DefaultResponseErrorHandler);
        assertEquals(clientHttpRequestInitializers, actualRestTemplateResult.getInterceptors());
        assertTrue(((DefaultUriBuilderFactory) uriTemplateHandler).getDefaultUriVariables().isEmpty());
        assertEquals(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT,
                ((DefaultUriBuilderFactory) uriTemplateHandler).getEncodingMode());
        assertEquals(1, messageConverters.get(2).getSupportedMediaTypes().size());
        assertEquals(2, messageConverters.get(1).getSupportedMediaTypes().size());
        ObjectMapper objectMapper = ((MappingJackson2HttpMessageConverter) messageConverters.get(6)).getObjectMapper();
        assertTrue(objectMapper
                .getPolymorphicTypeValidator() instanceof com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator);
        assertSame(objectMapper.getJsonFactory(), objectMapper.getFactory());
        assertTrue(objectMapper
                .getDeserializationContext() instanceof com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.Impl);
        assertTrue(objectMapper.getDateFormat() instanceof com.fasterxml.jackson.databind.util.StdDateFormat);
        assertTrue(objectMapper.getSerializerFactory() instanceof com.fasterxml.jackson.databind.ser.BeanSerializerFactory);
        assertTrue(
                objectMapper.getSubtypeResolver() instanceof com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver);
        assertTrue(objectMapper
                .getSerializerProviderInstance() instanceof com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl);
        assertTrue(objectMapper.getVisibilityChecker() instanceof VisibilityChecker.Std);
        assertNull(objectMapper.getPropertyNamingStrategy());
        assertTrue(objectMapper
                .getSerializerProvider() instanceof com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl);
    }
}

