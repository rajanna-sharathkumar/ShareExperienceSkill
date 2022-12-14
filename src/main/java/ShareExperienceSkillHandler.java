import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

import handlers.*;
import interceptors.GetUserMessageIntereceptor;
import interceptors.GetUserNameRequestIntereceptor;
import interceptors.SavePreviousIntentInterceptor;

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
                         new ShareExperienceIntentHandler(),
                         new SaveUserNameIntentHandler()
                     )
                     .addExceptionHandlers(new GenericExceptionHandler())
                     .addRequestInterceptors(new GetUserNameRequestIntereceptor())
                     .addResponseInterceptors(new SavePreviousIntentInterceptor())
                     .addRequestInterceptors(new GetUserMessageIntereceptor())
                     // Add your skill id below
                     //.withSkillId("")
                     .build();
    }

    public ShareExperienceSkillHandler() {
        super(getSkill());
    }

}
