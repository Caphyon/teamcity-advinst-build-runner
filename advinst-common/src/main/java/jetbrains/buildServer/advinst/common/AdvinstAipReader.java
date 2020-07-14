package jetbrains.buildServer.advinst.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Utility class that extract information by reading the AIP file directly.
 *
 * @author Ciprian Burca
 */
public class AdvinstAipReader {

  private final String mAipFile;
  Document mXmlDocument = null;

  /**
   * Class constructor.
   *
   * @param aAipFile Path to Advanced Installer project file (.AIP)
   */
  public AdvinstAipReader(String aAipFile) {
    this.mAipFile = aAipFile;
  }

  /**
   * Get the build names from the AIP.
   *
   * @return strings list containing the build names
   * @throws AdvinstException
   */
  public List<String> getBuilds() throws AdvinstException {
    List<String> aipBuilds = new ArrayList<String>();

    loadXmlFile();

    final String buildsXPAth = "/DOCUMENT/COMPONENT[@cid='caphyon.advinst.msicomp.BuildComponent']/ROW";
    XPath xPath = XPathFactory.newInstance().newXPath();
    NodeList buildRows;
    try {
      buildRows = (NodeList) xPath.evaluate(buildsXPAth, mXmlDocument, XPathConstants.NODESET);
    } catch (XPathExpressionException ex) {
      throw new AdvinstException("Failed read AIP builds. Exception: " + ex.getMessage(), ex);
    }
    for (int i = 0; i < buildRows.getLength(); i++) {
      Node buildRow = buildRows.item(i);
      Attr nameAttr = (Attr) buildRow.getAttributes().getNamedItem("BuildName");
      if (null != nameAttr) {
        aipBuilds.add(nameAttr.getValue());
      }
    }

    return aipBuilds;
  }

  public boolean isValidAip() throws AdvinstException {
    loadXmlFile();
    NodeList children = mXmlDocument.getChildNodes();
    if (children.getLength() != 1) {
      return false;
    }

    if (!"DOCUMENT".equals(children.item(1).getNodeName())) {
      return false;
    }

    Attr nameAttr = (Attr) children.item(1).getAttributes().getNamedItem("Type");
    if (null == nameAttr) {
      return false;
    }

    return true;
  }

  private void loadXmlFile() throws AdvinstException {
    try {
      if (null != mXmlDocument) {
        return;
      }

      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      mXmlDocument = documentBuilder.parse(new File(mAipFile));
    } catch (SAXException ex) {
      throw new AdvinstException("Failed to load AIP file. Exception: " + ex.getMessage(), ex);
    } catch (ParserConfigurationException ex) {
      throw new AdvinstException("Failed to load AIP file. Exception: " + ex.getMessage(), ex);
    } catch (IOException ex) {
      throw new AdvinstException("Failed to load AIP file. Exception: " + ex.getMessage(), ex);
    }
  }
}
