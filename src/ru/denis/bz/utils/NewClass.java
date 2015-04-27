/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.bz.utils;
// FirstEvents.java 
// События - нажатия клавиш на клавиатуре
import javax.swing.*; 
import java.awt.event.*; 
/**
 *
 * @author Denis
 */
public class NewClass extends JFrame{

 NewClass() { 
 super("FirstEvents"); 
 // при закрытии окна - выход
 setDefaultCloseOperation(EXIT_ON_CLOSE); 
 // регистрируем нашего слушателя
 addKeyListener(new KeyL()); 
 // выводим окно на экран
 setSize(200, 200); 
 setVisible(true); 
 } 
 public static void main(String[] args) { 
 new NewClass(); 
 } 
} 
// этот класс будет получать извещения о событиях
class KeyL implements KeyListener { 
 // печать символа
 public void keyTyped(KeyEvent k) { 
 System.out.println(k); 
 } 
 // нажатие клавиши
 public void keyPressed(KeyEvent k) { 
 System.out.println(k); 
 } 
 // отпускание нажатой клавиши
 public void keyReleased(KeyEvent k) { 
 System.out.println(k); 
 } 
}     

