<#import "common.ftl" as c>


<@c.page>
<div class="form-box">
    <div class="header">Get short link!</div>
    <div class="body">
        <form method="POST" action="/link">
            <input type="hidden" name="action" value="postlink">
            <div class="field">
                <div class="name">
                    <label for="link">Link</label>
                </div>
                <div class="value">
                    <input id="link" name="link" value="${link!}"/>
                </div>
            </div>

            <div class="button-field">
                <input type="submit" value="Get short link">
            </div>
            <#if error??>
            <div class="error">
                ${error}
            </div>
            </#if>
        </form>
    </div>
</div>


<div class="form-box">
    <div class="header">Get full link!</div>
    <div class="body">
        <form method="GET" action="/link">
            <input type="hidden" name="action" value="getlink">
            <div class="field">
                <div class="name">
                    <label for="link">Link</label>
                </div>
                <div class="value">
                    <input id="shortLink" name="shortLink" value="${shortLink!}"/>
                </div>
            </div>

            <div class="button-field">
                <input type="submit" value="Get full link">
            </div>
            <#if errorget??>
            <div class="error">
                ${errorget}
            </div>
            </#if>
        </form>
    </div>
</div>

</@c.page>
