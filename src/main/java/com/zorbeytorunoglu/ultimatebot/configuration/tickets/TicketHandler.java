package com.zorbeytorunoglu.ultimatebot.configuration.tickets;

import com.zorbeytorunoglu.ultimatebot.configuration.Configuration;
import com.zorbeytorunoglu.ultimatebot.configuration.ConfigurationProvider;
import com.zorbeytorunoglu.ultimatebot.configuration.Resource;
import com.zorbeytorunoglu.ultimatebot.configuration.YamlConfiguration;
import com.zorbeytorunoglu.ultimatebot.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TicketHandler {

    private final Resource ticketPanelsResource;
    private final Configuration configuration;
    private final List<TicketPanel> ticketPanels;

    public TicketHandler(Resource ticketPanelsResource) {
        this.ticketPanelsResource=ticketPanelsResource;

        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(ticketPanelsResource.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.ticketPanels=new ArrayList<>();

        loadPanels(configuration);

    }

    public List<TicketPanel> getTicketPanels() {
        return ticketPanels;
    }

    public Resource getTicketPanelsResource() {
        return ticketPanelsResource;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    private void loadPanels(Configuration configuration) {
        if (configuration.getKeys().isEmpty()) return;
        configuration.getKeys().forEach(panelId -> {
            TicketPanel ticketPanel=loadPanel(panelId,configuration);
            ticketPanels.add(ticketPanel);
        });
    }

    private TicketPanel loadPanel(String panelId, Configuration configuration) {
        TicketPanel ticketPanel=new TicketPanel(panelId);
        if (configuration.getString(panelId+".title")!="")
            ticketPanel.setTitle(configuration.getString(panelId+".title"));
        if (configuration.getString(panelId+".author")!="")
            ticketPanel.setAuthor(configuration.getString(panelId+".author"));
        if (configuration.getString(panelId+".author-url")!="")
            ticketPanel.setAuthorUrl(configuration.getString(panelId+".author-url"));
        if (configuration.getString(panelId+".author-img-url")!="")
            ticketPanel.setAuthorImgUrl(configuration.getString(panelId+".author-img-url"));
        if (configuration.getString(panelId+".color")!="")
            ticketPanel.setColor(BotUtils.getColor(configuration.getString(panelId+".color")));
        if (configuration.getString(panelId+".footer")!="")
            ticketPanel.setFooter(configuration.getString(panelId+".footer"));
        if (configuration.getString(panelId+".footer-img-url")!="")
            ticketPanel.setFooterImgUrl(configuration.getString(panelId+".footer-img-url"));
        if (configuration.getString(panelId+".thumbnail-img-url")!="")
            ticketPanel.setThumbnailImgUrl(configuration.getString(panelId+".thumbnail-img-url"));
        if (configuration.getString(panelId+".description")!="")
            ticketPanel.setDescription(configuration.getString(panelId+".description"));

        EmbedBuilder embedBuilder=new EmbedBuilder().setAuthor(ticketPanel.getAuthor(),
                        ticketPanel.getAuthorUrl(), ticketPanel.getAuthorImgUrl())
                .setColor(ticketPanel.getColor()).setDescription(ticketPanel.getDescription())
                .setFooter(ticketPanel.getFooter(),ticketPanel.getFooterImgUrl())
                .setThumbnail(ticketPanel.getThumbnailImgUrl()).setTitle(ticketPanel.getTitle());

        loadFields(panelId,configuration).forEach(field -> embedBuilder.addField(field));

        ticketPanel.setTicketEmbed(embedBuilder.build());

        ticketPanel.setTicketButtons(loadButtons(panelId,configuration));

        return ticketPanel;

    }

    private List<MessageEmbed.Field> loadFields(String panelId, Configuration configuration) {
        if (configuration.getSection(panelId+".fields")==null) return Collections.EMPTY_LIST;
        Collection<String> keys=configuration.getSection(panelId+".fields").getKeys();
        List<MessageEmbed.Field> fieldList=new ArrayList<>();
        for (String s:keys) {
            String fieldDescription=configuration.getString(panelId+".fields."+s+".description").equalsIgnoreCase("none") ?
                    null : configuration.getString(panelId+".fields."+s+".description");
            String fieldTitle=configuration.getString(panelId+".fields."+s+".title").equalsIgnoreCase("none") ?
                    null : configuration.getString(panelId+".fields."+s+".title");
            fieldList.add(new MessageEmbed.Field(fieldTitle,fieldDescription,false));
        }
        return fieldList;
    }

    private List<TicketButton> loadButtons(String panelId, Configuration configuration) {

        if (configuration.getSection(panelId+".buttons")==null) return null;

        Collection<String> keys=configuration.getSection(panelId+".buttons").getKeys();
        List<TicketButton> buttonList=new ArrayList<>();

        for (String s:keys) {

            TicketButton ticketButton=new TicketButton(configuration.getString(panelId+".buttons."+s+".id"));
            if (configuration.getString(panelId+".buttons."+s+".label")!="" && configuration.getString(panelId+".buttons."+s+".emoji")!="") {
                ticketButton.setLabel(configuration.getString(panelId+".buttons."+s+".label"));
                ticketButton.setEmojiId(configuration.getString(panelId+".buttons."+s+".emoji"));
                ticketButton.setButton(Button.secondary(ticketButton.getId(),
                        ticketButton.getLabel()).withEmoji(Emoji.fromMarkdown(ticketButton.getEmojiId())));
            }

            if (configuration.getString(panelId+".buttons."+s+".ticket.category")!="")
                ticketButton.setTicketCategoryId(configuration.getString(panelId+".buttons."+s+".ticket.category"));

            if (configuration.getString(panelId+".buttons."+s+".ticket.name-format")!="")
                ticketButton.setTicketNameFormat(configuration.getString(panelId+".buttons."+s+".ticket.name-format"));

            if (configuration.getDefault(panelId+".buttons."+s+".ticket.roles-to-be-added")!=null) {
                List<String> roles=new ArrayList<>();
                roles.addAll(configuration.getStringList(panelId+".buttons."+s+".ticket.roles-to-be-added"));
                ticketButton.setTicketRolesToBeAdded(roles);
            }

            if (configuration.getDefault(panelId+".buttons."+s+".ticket.ping-roles")!=null) {
                List<String> roles=new ArrayList<>();
                roles.addAll(configuration.getStringList(panelId+".buttons."+s+".ticket.ping-roles"));
                ticketButton.setTicketPingRoles(roles);
            }

            if (configuration.getDefault(panelId+".buttons."+s+".ticket.start-embed")!=null) {
                EmbedBuilder embedBuilder=new EmbedBuilder();
                if (configuration.getString(panelId+".buttons."+s+".ticket.start-embed.author")!="")
                    embedBuilder.setAuthor(configuration.getString(panelId+".buttons."+s+".ticket.start-embed.author"));
                if (configuration.getString(panelId+".buttons."+s+".ticket.start-embed.author-url")!="") {
                    embedBuilder.setAuthor(configuration.getString(panelId+".buttons."+s+".ticket.start-embed.author"),
                            configuration.getString(panelId+".buttons."+s+".ticket.start-embed.author-url"));
                }
                if (configuration.getString(panelId+".buttons."+s+".ticket.start-embed.author-img-url")!="") {
                    embedBuilder.setAuthor(configuration.getString(panelId+".buttons."+s+".ticket.start-embed.author"),
                            configuration.getString(panelId+".buttons."+s+".ticket.start-embed.author-url"),
                            configuration.getString(panelId+".buttons."+s+".ticket.start-embed.author-img-url"));
                }
                if (configuration.getString(panelId+".buttons."+s+".ticket.start-embed.title")!="") {
                    embedBuilder.setTitle(configuration.getString(panelId+".buttons."+s+".ticket.start-embed.title"));
                }
                if (configuration.getString(panelId+".buttons."+s+".ticket.start-embed.description")!="") {
                    embedBuilder.setDescription(configuration.getString(panelId+".buttons."+s+".ticket.start-embed.description"));
                }
                if (configuration.getString(panelId+".buttons."+s+".ticket.start-embed.color")!="") {
                    embedBuilder.setColor(BotUtils.getColor(configuration.getString(panelId+".buttons."+s+".ticket.start-embed.color")));
                }
                ticketButton.setTicketEmbed(embedBuilder.build());
            }

            buttonList.add(ticketButton);

        }

        return buttonList;

    }

}
