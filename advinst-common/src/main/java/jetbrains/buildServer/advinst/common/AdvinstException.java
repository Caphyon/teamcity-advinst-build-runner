package jetbrains.buildServer.advinst.common;

/**
 * Advanced Installer high level exception
 *
 * @author Ciprian Burca
 */
public class AdvinstException extends Exception
{

  public AdvinstException(String message, Throwable t)
  {
    super(message, t);
  }
}
