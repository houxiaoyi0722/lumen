package com.sang.system.service.dict.impl;

import com.sang.common.domain.dict.entity.Dictionary;
import com.sang.common.domain.dict.entity.DictionaryItem;
import com.sang.system.domain.dict.param.DataDictionaryQry;
import com.sang.system.domain.dict.repo.DictionaryRepository;
import io.ebean.PagedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DictionaryServiceImplTest {

    @Mock
    private DictionaryRepository mockDictionaryRepository;

    @InjectMocks
    private DictionaryServiceImpl dictionaryServiceImplUnderTest;

    @Test
    void testDictionaryList() {
        // Setup
        final DataDictionaryQry dataDictionaryQry = new DataDictionaryQry();
        when(mockDictionaryRepository.getDictionaryList(any(DataDictionaryQry.class))).thenReturn(PagedList.emptyList());

        // Run the test
        final PagedList<Dictionary> result = dictionaryServiceImplUnderTest.dictionaryList(dataDictionaryQry);

        // Verify the results
    }

    @Test
    void testDictionaryList_DictionaryRepositoryReturnsNoItem() {
        // Setup
        final DataDictionaryQry dataDictionaryQry = new DataDictionaryQry();
        when(mockDictionaryRepository.getDictionaryList(any(DataDictionaryQry.class))).thenReturn(PagedList.emptyList());

        // Run the test
        final PagedList<Dictionary> result = dictionaryServiceImplUnderTest.dictionaryList(dataDictionaryQry);

        // Verify the results
    }

    @Test
    void testFindOne() {
        // Setup
        // Configure DictionaryRepository.findById(...).
        final Dictionary dictionary = new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment");
        when(mockDictionaryRepository.findById(0L)).thenReturn(dictionary);

        // Run the test
        final Dictionary result = dictionaryServiceImplUnderTest.findOne(0L);

        // Verify the results
    }

    @Test
    void testFindOne_DictionaryRepositoryReturnsNull() {
        // Setup
        when(mockDictionaryRepository.findById(0L)).thenReturn(null);

        // Run the test
        final Dictionary result = dictionaryServiceImplUnderTest.findOne(0L);

        // Verify the results
    }

    @Test
    void testSave() {
        // Setup
        final Dictionary dictionary = new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment");

        // Run the test
        dictionaryServiceImplUnderTest.save(dictionary);

        // Verify the results
    }

    @Test
    void testInsert() {
        // Setup
        final Dictionary dictionary = new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment");

        // Run the test
        dictionaryServiceImplUnderTest.insert(dictionary);

        // Verify the results
    }

    @Test
    void testUpdate() {
        // Setup
        final Dictionary dictionary = new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment");

        // Run the test
        dictionaryServiceImplUnderTest.update(dictionary);

        // Verify the results
    }

    @Test
    void testDelete() {
        // Setup
        final Dictionary dictionary = new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment");
        when(mockDictionaryRepository.delete(any(Dictionary.class))).thenReturn(false);

        // Run the test
        dictionaryServiceImplUnderTest.delete(dictionary);

        // Verify the results
        verify(mockDictionaryRepository).delete(any(Dictionary.class));
    }

    @Test
    void testSaveAll() {
        // Setup
        final List<Dictionary> dictionaries = List.of(new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment"));
        when(mockDictionaryRepository.saveAll(List.of(new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment")))).thenReturn(0);

        // Run the test
        dictionaryServiceImplUnderTest.saveAll(dictionaries);

        // Verify the results
        verify(mockDictionaryRepository).saveAll(List.of(new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment")));
    }

    @Test
    void testDeleteAll() {
        // Setup
        final List<Dictionary> dictionaries = List.of(new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment"));
        when(mockDictionaryRepository.deleteAll(List.of(new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment")))).thenReturn(0);

        // Run the test
        dictionaryServiceImplUnderTest.deleteAll(dictionaries);

        // Verify the results
        verify(mockDictionaryRepository).deleteAll(List.of(new Dictionary(List.of(new DictionaryItem(null, "itemValue", "itemKey", "comment")), "groupId", "groupName", "comment")));
    }
}
