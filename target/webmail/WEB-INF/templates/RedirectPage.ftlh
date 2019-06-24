<#import "common.ftl" as c>


<@c.page>

<div class="form-box">
    <div class="header">Go to website!</div>
    <div class="body">
        <form method="GET" action="/redirect">
            <input type="hidden" name="action" value="action">
            <div class="field">
                <div class="name">
                    <label for="link">Short Link</label>
                </div>
                <div class="value">
                    <input id="link" name="link" value="${link!}"/>
                </div>
            </div>

            <div class="button-field">
                <input type="submit" value="Go!">
            </div>
            <#if errorredirect??>
            <div class="error">
                ${errorredirect}
            </div>
            </#if>
        </form>
    </div>
</div>
</@c.page>
