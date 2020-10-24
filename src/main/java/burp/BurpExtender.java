package burp;
import java.awt.*;

// intellij forms: https://www.secpulse.com/archives/124593.html
public class BurpExtender implements IBurpExtender,  ITab, ISessionHandlingAction
{
    private IExtensionHelpers helpers;
    private IBurpExtenderCallbacks callbacks;
    public BurpTab tab;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks)
    {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        callbacks.setExtensionName("TrafficRedirector");

        // register ourselves as a session handling action
        callbacks.registerSessionHandlingAction(this);

        // add new JPanel tab
        tab = new BurpTab(callbacks);
        callbacks.addSuiteTab(this);

        callbacks.printOutput("Traffic Redirector loaded");

    }

    @Override
    public String getTabCaption() {
        return "Traffic Redirector";
    }

    @Override
    public Component getUiComponent() {
        return tab.getRootComponent();
    }

    @Override
    public String getActionName() {
        return "Traffic Redirector";
    }

    @Override
    public void performAction(IHttpRequestResponse currentRequest, IHttpRequestResponse[] macroItems) {
        // get the HTTP service for the request
        IHttpService httpService = currentRequest.getHttpService();

        // if the host is HOST_FROM, change it to HOST_TO
        if (tab.fromHost.equalsIgnoreCase(httpService.getHost()))
            currentRequest.setHttpService(helpers.buildHttpService(
                    tab.toHost, httpService.getPort(), httpService.getProtocol()));
    }
}