package DevRoomApplication.utils.game.adventurelib;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;

public class TitleUtils {
    public void showMyTitle(final Audience target, String text, NamedTextColor color) {
        Component mainTitle = Component.text(text, color);
        Component subtitle = Component.text("", color);

        Title title = Title.title(mainTitle, subtitle);

        target.showTitle(title);
    }
}
