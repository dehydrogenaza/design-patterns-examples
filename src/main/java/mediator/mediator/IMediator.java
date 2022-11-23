package mediator.mediator;

import mediator.employees.EmployeeBase;

//Wyodrębnienie interfejsu mediatora zamiast poleganie na określonej implementacji pozwoli potem łatwiej "podmienić"
// mediator, jeśli zajdzie taka potrzeba.
public interface IMediator {
    void sendToAll(EmployeeBase sender, String request);
    void sendToOthers(EmployeeBase sender, String request);
    void sendToCEO(EmployeeBase sender, String request);

    void CEOBoast();

}
