package fr.ecolnum.projectapi.util;

import fr.ecolnum.projectapi.exception.IdNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GenericUtility {

    public static <Type, Id>
    Set<Type> extractSetFromRepository(
            JpaRepository<Type, Id> repo,
            List<Id> listId) throws IdNotFoundException {

        Set<Type> container = new HashSet<Type>();

        if (listId == null) return container;

        for (Id id : listId) {
            Optional<Type> optionalType = repo.findById(id);

            if (optionalType.isEmpty()) throw new IdNotFoundException("candidate " + id + ": not found");

            container.add(optionalType.get());
        }
        return container;
    }

    public static String convertStringToJsonData(String str) {
        return "\"{ \"error\":\"" + str + "\"}\"";
    }
}
