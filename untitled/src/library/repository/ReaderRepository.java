package library.repository;

import library.model.Reader;

public interface ReaderRepository {
    void add (Reader reader);
    Reader findById (int id);
    Reader[] getAll ();
}
