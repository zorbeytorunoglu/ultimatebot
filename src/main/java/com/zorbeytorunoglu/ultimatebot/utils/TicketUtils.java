package com.zorbeytorunoglu.ultimatebot.utils;

import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketHandler;
import com.zorbeytorunoglu.ultimatebot.configuration.tickets.TicketPanel;

public class TicketUtils {

    public static boolean isValidTicketPanelId(String panelId, TicketHandler ticketHandler) {
        if (!ticketHandler.getTicketPanelsResource().getFile().exists()) return false;
        if (!ticketHandler.getConfiguration().getKeys().contains(panelId)) return false;
        return true;
    }

    public static TicketPanel getPanel(String panelId, TicketHandler ticketHandler) {
        if (ticketHandler.getTicketPanels().isEmpty()) return null;
        return ticketHandler.getTicketPanels().stream().filter(ticketPanel ->
                ticketPanel.getPanelId().equals(panelId)).findFirst().orElse(null);
    }



}
