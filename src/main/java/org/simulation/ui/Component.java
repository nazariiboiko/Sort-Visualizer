package org.simulation.ui;

import org.simulation.Mediator;

public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
