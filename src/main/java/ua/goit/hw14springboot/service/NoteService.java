package ua.goit.hw14springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.goit.hw14springboot.exception.NoteNotFoundException;
import ua.goit.hw14springboot.model.Note;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final Map<Long, Note> content = new ConcurrentHashMap<>();
    private final Random random;
    private static final String EXCEPTION_MESSAGE = "Note with id %d not found.";

    public List<Note> listAll() {
        Collection<Note> notes = content.values();
        return new ArrayList<>(notes);
    }

    public Note add(Note note) {
        long id = random.nextLong();
        Note copyOfNote = Note.builder()
                .id(id)
                .content(note.getContent())
                .title(note.getTitle())
                .build();
        content.put(id, copyOfNote);
        return copyOfNote;
    }

    public void deleteById(long id) {
        if (!content.containsKey(id)) {
            throw new NoteNotFoundException(EXCEPTION_MESSAGE.formatted(id));
        }
        content.remove(id);
    }

    public void update(Note note) {
        if (!content.containsKey(note.getId())) {
            throw new NoteNotFoundException(EXCEPTION_MESSAGE.formatted(note.getId()));
        }
        Note newNote = Note.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
        content.put(note.getId(), newNote);
    }

    public Note getById(long id) {
        if (!content.containsKey(id)) {
            throw new NoteNotFoundException(EXCEPTION_MESSAGE.formatted(id));
        }
        Note noteFromMap = content.get(id);
        return Note.builder()
                .id(id)
                .title(noteFromMap.getTitle())
                .content(noteFromMap.getContent())
                .build();
    }
}
