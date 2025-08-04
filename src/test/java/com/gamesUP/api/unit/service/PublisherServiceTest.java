package com.gamesup.api.unit.service;

import com.gamesup.api.dto.publisher.PublisherDto;
import com.gamesup.api.dto.publisher.CreatePublisherDto;
import com.gamesup.api.dto.publisher.UpdatePublisherDto;
import com.gamesup.api.exception.ResourceNotFoundException;
import com.gamesup.api.model.Publisher;
import com.gamesup.api.repository.PublisherRepository;
import com.gamesup.api.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PublisherServiceTest {

    private PublisherRepository publisherRepository;
    private PublisherService publisherService;

    @BeforeEach
    void setUp() {
        publisherRepository = mock(PublisherRepository.class);
        publisherService = new PublisherService(publisherRepository);
    }

    @Test
    void getAllPublishers_shouldReturnList() {
        when(publisherRepository.findAll()).thenReturn(Arrays.asList(
                new Publisher(1L, "Publisher1", null),
                new Publisher(2L, "Publisher2", null)
        ));

        List<PublisherDto> result = publisherService.getAllPublishers();

        assertEquals(2, result.size());
        assertEquals("Publisher1", result.getFirst().name());
    }

    @Test
    void getPublisherById_shouldReturnPublisher() {
        Publisher publisher = new Publisher(1L, "Publisher1", null);
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        PublisherDto result = publisherService.getPublisherById(1L);

        assertEquals("Publisher1", result.name());
    }

    @Test
    void getPublisherById_shouldThrowIfNotFound() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> publisherService.getPublisherById(1L));
    }

    @Test
    void createPublisher_shouldSaveAndReturnDto() {
        CreatePublisherDto dto = new CreatePublisherDto("New Publisher");
        Publisher saved = new Publisher(1L, "New Publisher", null);

        when(publisherRepository.save(any(Publisher.class))).thenReturn(saved);

        PublisherDto result = publisherService.createPublisher(dto);

        assertEquals("New Publisher", result.name());
        verify(publisherRepository).save(any(Publisher.class));
    }

    @Test
    void updatePublisher_shouldUpdateName() {
        Publisher existing = new Publisher(1L, "Old Name", null);
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(existing);

        UpdatePublisherDto dto = new UpdatePublisherDto("Updated Name");
        PublisherDto result = publisherService.updatePublisher(1L, dto);

        assertEquals("Updated Name", result.name());
    }

    @Test
    void deletePublisher_shouldCallDelete() {
        Publisher existing = new Publisher(1L, "To Delete", null);
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(existing));

        publisherService.deletePublisher(1L);

        verify(publisherRepository).deleteById(1L);
    }
}
