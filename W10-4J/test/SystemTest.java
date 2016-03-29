package test;

import static org.junit.Assert.*;

import org.junit.Test;
import Parser.Parser;

public class SystemTest {
	
	@Test
	public void testParse() {
		Parser p = new Parser();
		String command;
		
		command = "add";
		assertEquals("Invalid command format",p.parse(command));
		
		command = "add a";
		assertEquals("1a has been added.",p.parse(command));
		
		command = "ls";
		assertEquals("0<table width=\"100%\" style=\"margin:0px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"> Event </th><th style=\"width:15%;\" align=\"left\"> Date </th><th style=\"width:12%;\" align=\"left\"> Start Time </th><th style=\"width:12%;\" align=\"left\"> End Time </th><th style=\"width:25%;\" align=\"left\"> Details </th><th style=\"width:13%;\" align=\"left\"> Repeat </th></tr><tr style=\"border-bottom:1px solid #E5E4E2\"><td align=\"right\"><font color=#000000>1)</font></td><td><font color=#000000>a</font></td><td></td><td></td><td></td><td></td>",p.parse(command));
		
		command = "rm 1";
		assertEquals("1a has been deleted.",p.parse(command));
		
		command = "ls";
		assertEquals("0<table width=\"100%\" style=\"margin:0px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"> Event </th><th style=\"width:15%;\" align=\"left\"> Date </th><th style=\"width:12%;\" align=\"left\"> Start Time </th><th style=\"width:12%;\" align=\"left\"> End Time </th><th style=\"width:25%;\" align=\"left\"> Details </th><th style=\"width:13%;\" align=\"left\"> Repeat </th></tr>",p.parse(command));
		
		command = "undo";
		assertEquals("1Undo successful.",p.parse(command));
		
		command = "ls";
		assertEquals("0<table width=\"100%\" style=\"margin:0px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"> Event </th><th style=\"width:15%;\" align=\"left\"> Date </th><th style=\"width:12%;\" align=\"left\"> Start Time </th><th style=\"width:12%;\" align=\"left\"> End Time </th><th style=\"width:25%;\" align=\"left\"> Details </th><th style=\"width:13%;\" align=\"left\"> Repeat </th></tr><tr style=\"border-bottom:1px solid #E5E4E2\"><td align=\"right\"><font color=#000000>1)</font></td><td><font color=#000000>a</font></td><td></td><td></td><td></td><td></td>",p.parse(command));
		
		command = "add b date 30/3 start 1930";
		assertEquals("1b has been added.",p.parse(command));
		
		command = "ls";
		assertEquals("0<table width=\"100%\" style=\"margin:0px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"> Event </th><th style=\"width:15%;\" align=\"left\"> Date </th><th style=\"width:12%;\" align=\"left\"> Start Time </th><th style=\"width:12%;\" align=\"left\"> End Time </th><th style=\"width:25%;\" align=\"left\"> Details </th><th style=\"width:13%;\" align=\"left\"> Repeat </th></tr><tr style=\"border-bottom:1px solid #E5E4E2\"><td align=\"right\"><font color=#000000>1)</font></td><td><font color=#000000>a</font></td><td></td><td></td><td></td><td></td><tr style=\"border-bottom:1px solid #E5E4E2\"><td align=\"right\"><font color=#000000>2)</font></td><td><font color=#000000>b</font></td><td><font color=#000000>2016/03/30</font></td><td><font color=#000000>19:30</font></td><td></td><td></td>",p.parse(command));
		
		command = "rm 2";
		assertEquals("1b has been deleted.",p.parse(command));
		
		command = "ls";
		assertEquals("0<table width=\"100%\" style=\"margin:0px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"> Event </th><th style=\"width:15%;\" align=\"left\"> Date </th><th style=\"width:12%;\" align=\"left\"> Start Time </th><th style=\"width:12%;\" align=\"left\"> End Time </th><th style=\"width:25%;\" align=\"left\"> Details </th><th style=\"width:13%;\" align=\"left\"> Repeat </th></tr><tr style=\"border-bottom:1px solid #E5E4E2\"><td align=\"right\"><font color=#000000>1)</font></td><td><font color=#000000>a</font></td><td></td><td></td><td></td><td></td>",p.parse(command));
		
		command = "rm 1";
		assertEquals("1a has been deleted.",p.parse(command));
		
		command = "ls";
		assertEquals("0<table width=\"100%\" style=\"margin:0px;\"><tr style=\"border-bottom:1px solid #B6B6B4\"><th style=\"width:3%;\"></th><th style=\"width:20%;\" align=\"left\"> Event </th><th style=\"width:15%;\" align=\"left\"> Date </th><th style=\"width:12%;\" align=\"left\"> Start Time </th><th style=\"width:12%;\" align=\"left\"> End Time </th><th style=\"width:25%;\" align=\"left\"> Details </th><th style=\"width:13%;\" align=\"left\"> Repeat </th></tr>",p.parse(command));
		
		command = "setdir";
		assertEquals("Invalid command format",p.parse(command));
		
		command = "retrieve";
		assertEquals("Invalid command format",p.parse(command));
		
		command = "";
		assertEquals("Invalid command format",p.parse(command));
	}
}