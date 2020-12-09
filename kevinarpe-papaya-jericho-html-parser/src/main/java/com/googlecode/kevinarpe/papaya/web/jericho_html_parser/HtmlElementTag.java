package com.googlecode.kevinarpe.papaya.web.jericho_html_parser;

/*-
 * #%L
 * This file is part of Papaya.
 * %%
 * Copyright (C) 2013 - 2020 Kevin Connor ARPE (kevinarpe@gmail.com)
 * %%
 * Papaya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GPL Classpath Exception:
 * This project is subject to the "Classpath" exception as provided in
 * the LICENSE file that accompanied this code.
 * 
 * Papaya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Papaya.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import com.googlecode.kevinarpe.papaya.argument.StringArgs;
import net.htmlparser.jericho.HTMLElementName;

/**
 * This enum was generated from {@link HTMLElementName} using this UNIX filter:
 * <p>
 * {@code grep -F 'public static final String' | perl -p -e 's/^.*String\s+(\S+)\s*=.*$/    $1(HTMLElementName.$1),/'}
 *
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public enum HtmlElementTag {

    A(HTMLElementName.A),
    ABBR(HTMLElementName.ABBR),
    ACRONYM(HTMLElementName.ACRONYM),
    ADDRESS(HTMLElementName.ADDRESS),
    APPLET(HTMLElementName.APPLET),
    AREA(HTMLElementName.AREA),
    ARTICLE(HTMLElementName.ARTICLE),
    ASIDE(HTMLElementName.ASIDE),
    AUDIO(HTMLElementName.AUDIO),
    B(HTMLElementName.B),
    BASE(HTMLElementName.BASE),
    BASEFONT(HTMLElementName.BASEFONT),
    BDI(HTMLElementName.BDI),
    BDO(HTMLElementName.BDO),
    BIG(HTMLElementName.BIG),
    BLOCKQUOTE(HTMLElementName.BLOCKQUOTE),
    BODY(HTMLElementName.BODY),
    BR(HTMLElementName.BR),
    BUTTON(HTMLElementName.BUTTON),
    CANVAS(HTMLElementName.CANVAS),
    CAPTION(HTMLElementName.CAPTION),
    CENTER(HTMLElementName.CENTER),
    CITE(HTMLElementName.CITE),
    CODE(HTMLElementName.CODE),
    COL(HTMLElementName.COL),
    COLGROUP(HTMLElementName.COLGROUP),
    COMMAND(HTMLElementName.COMMAND),
    DATALIST(HTMLElementName.DATALIST),
    DD(HTMLElementName.DD),
    DEL(HTMLElementName.DEL),
    DETAILS(HTMLElementName.DETAILS),
    DFN(HTMLElementName.DFN),
    DIR(HTMLElementName.DIR),
    DIV(HTMLElementName.DIV),
    DL(HTMLElementName.DL),
    DT(HTMLElementName.DT),
    EM(HTMLElementName.EM),
    EMBED(HTMLElementName.EMBED),
    FIELDSET(HTMLElementName.FIELDSET),
    FIGCAPTION(HTMLElementName.FIGCAPTION),
    FIGURE(HTMLElementName.FIGURE),
    FONT(HTMLElementName.FONT),
    FOOTER(HTMLElementName.FOOTER),
    FORM(HTMLElementName.FORM),
    FRAME(HTMLElementName.FRAME),
    FRAMESET(HTMLElementName.FRAMESET),
    H1(HTMLElementName.H1),
    H2(HTMLElementName.H2),
    H3(HTMLElementName.H3),
    H4(HTMLElementName.H4),
    H5(HTMLElementName.H5),
    H6(HTMLElementName.H6),
    HEAD(HTMLElementName.HEAD),
    HEADER(HTMLElementName.HEADER),
    HGROUP(HTMLElementName.HGROUP),
    HR(HTMLElementName.HR),
    HTML(HTMLElementName.HTML),
    I(HTMLElementName.I),
    IFRAME(HTMLElementName.IFRAME),
    IMG(HTMLElementName.IMG),
    INPUT(HTMLElementName.INPUT),
    INS(HTMLElementName.INS),
    ISINDEX(HTMLElementName.ISINDEX),
    KBD(HTMLElementName.KBD),
    KEYGEN(HTMLElementName.KEYGEN),
    LABEL(HTMLElementName.LABEL),
    LEGEND(HTMLElementName.LEGEND),
    LI(HTMLElementName.LI),
    LINK(HTMLElementName.LINK),
    MAP(HTMLElementName.MAP),
    MARK(HTMLElementName.MARK),
    MENU(HTMLElementName.MENU),
    META(HTMLElementName.META),
    METER(HTMLElementName.METER),
    NAV(HTMLElementName.NAV),
    NOFRAMES(HTMLElementName.NOFRAMES),
    NOSCRIPT(HTMLElementName.NOSCRIPT),
    OBJECT(HTMLElementName.OBJECT),
    OL(HTMLElementName.OL),
    OPTGROUP(HTMLElementName.OPTGROUP),
    OPTION(HTMLElementName.OPTION),
    OUTPUT(HTMLElementName.OUTPUT),
    P(HTMLElementName.P),
    PARAM(HTMLElementName.PARAM),
    PRE(HTMLElementName.PRE),
    PROGRESS(HTMLElementName.PROGRESS),
    Q(HTMLElementName.Q),
    RP(HTMLElementName.RP),
    RT(HTMLElementName.RT),
    RUBY(HTMLElementName.RUBY),
    S(HTMLElementName.S),
    SAMP(HTMLElementName.SAMP),
    SCRIPT(HTMLElementName.SCRIPT),
    SECTION(HTMLElementName.SECTION),
    SELECT(HTMLElementName.SELECT),
    SMALL(HTMLElementName.SMALL),
    SOURCE(HTMLElementName.SOURCE),
    SPAN(HTMLElementName.SPAN),
    STRIKE(HTMLElementName.STRIKE),
    STRONG(HTMLElementName.STRONG),
    STYLE(HTMLElementName.STYLE),
    SUB(HTMLElementName.SUB),
    SUMMARY(HTMLElementName.SUMMARY),
    SUP(HTMLElementName.SUP),
    TABLE(HTMLElementName.TABLE),
    TBODY(HTMLElementName.TBODY),
    TD(HTMLElementName.TD),
    TEXTAREA(HTMLElementName.TEXTAREA),
    TFOOT(HTMLElementName.TFOOT),
    TH(HTMLElementName.TH),
    THEAD(HTMLElementName.THEAD),
    TIME(HTMLElementName.TIME),
    TITLE(HTMLElementName.TITLE),
    TR(HTMLElementName.TR),
    TT(HTMLElementName.TT),
    U(HTMLElementName.U),
    UL(HTMLElementName.UL),
    VAR(HTMLElementName.VAR),
    VIDEO(HTMLElementName.VIDEO),
    WBR(HTMLElementName.WBR),
    ;
    /**
     * Ex: {@link HTMLElementName#TD}
     */
    public final String tag;

    private HtmlElementTag(String tag) {

        this.tag = StringArgs.checkNotEmptyOrWhitespace(tag, "tag");
    }
}
