public class TattleEntry
{
    public String eventName;
    public String intro;
    public String level;
    public String hp;
    public String defense;
    public String attackPower;
    public String superguardable;
    public String status;
    public String weakness;
    public String immunity;
    public String outro;

    public TattleEntry()
    {
        this.eventName = "";
        this.intro = "";
        this.level = "";
        this.hp = "";
        this.defense = "";
        this.attackPower = "";
        this.superguardable = "";
        this.status = "";
        this.weakness = "";
        this.immunity = "";
        this.outro = "";
    }

    public TattleEntry(String nEventName, String nIntro, String nLevel, String nhp, String nDefense, String nAttackPower, String nSuperguardable, String nStatus, String nWeakness, String nImmunity, String nOutro)
    {
        this.eventName = nEventName;
        this.intro = nIntro;
        this.level = nLevel;
        this.hp = nhp;
        this.defense = nDefense;
        this.attackPower = nAttackPower;
        this.superguardable = nSuperguardable;
        this.status = nStatus;
        this.weakness = nWeakness;
        this.immunity = nImmunity;
        this.intro = nOutro;
    }

    public String listEntry()
    {
        String str = this.eventName + " " + "this.intro" + " " + this.level + " " + this.hp + " " + this.defense + " " + this.attackPower + " " + this.superguardable + " " + this.status + " " + this.weakness + " " + this.immunity + " " + "this.outro";
        return str;
    }
}