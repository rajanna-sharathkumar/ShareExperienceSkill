import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

import handlers.*;

public class ShareExperienceSkillHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                     .addRequestHandlers(
                         new LaunchHandler(),
                         new CancelIntentHandler(),
                         new HelpIntentHandler(),
                         new NoIntentHandler(),
                         new YesIntentHandler(),
                         new SimpleHelloIntentHandler(),
                         new ShareExperienceIntentHandler()
                     )
                     .addExceptionHandlers(new GenericExceptionHandler())
                     // Add your skill id below
                     //.withSkillId("")
                     .build();
    }

    public ShareExperienceSkillHandler() {
        super(getSkill());
    }

}
