package ru.alex.NauJava.services;

import java.util.List;

public interface ContactService {
    /**
     *Удаляет телефоны по списку ID. Если хотя бы один телефон не найден, транзакция откатывается.
     *
     * @param contactIds Список ID контактов для удаления.
     * @return true, если все контакты успешно удалены, иначе false.
     */
    boolean deleteContactsByIds(List<Long> contactIds);
}
