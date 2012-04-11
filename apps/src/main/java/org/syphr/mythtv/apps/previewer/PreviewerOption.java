/*
 * Copyright 2011-2012 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.mythtv.apps.previewer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public enum PreviewerOption
{
    HELP(new Option("h", "help", false, "show this usage text")),

    @SuppressWarnings("static-access")
    HOST(OptionBuilder.isRequired().hasArg().withArgName("host").withDescription("remote host").withLongOpt("host").create('o')),
    @SuppressWarnings("static-access")
    PORT(OptionBuilder.hasArg().withType(Number.class).withArgName("port").withDescription("remote port").withLongOpt("port").create('p')),
    @SuppressWarnings("static-access")
    TIMEOUT(OptionBuilder.hasArg().withType(Number.class).withArgName("timeout").withDescription("connection timeout").withLongOpt("timeout").create('t'));

    private final Option option;

    private PreviewerOption(Option option)
    {
        this.option = option;
    }

    public Option getOption()
    {
        return option;
    }

    public boolean hasOption(CommandLine cl)
    {
        return cl.hasOption(getOption().getOpt());
    }

    public String getValue(CommandLine cl)
    {
        return cl.getOptionValue(getOption().getOpt());
    }

    public Number getNumberValue(CommandLine cl) throws ParseException
    {
        return (Number)cl.getParsedOptionValue(getOption().getOpt());
    }

    public static Options getOptions()
    {
        Options options = new Options();

        for (PreviewerOption option : values())
        {
            options.addOption(option.getOption());
        }

        return options;
    }
}
